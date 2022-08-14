package networking;

import com.badlogic.gdx.utils.Disposable;
import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.NcsPacketListener;
import com.github.terefang.ncs.common.NcsStateListener;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import com.github.terefang.ncs.server.NcsServerHelper;
import com.github.terefang.ncs.server.NcsServerService;
import networking.packet.in.AbstractPacketIn;
import networking.packet.out.AbstractPacketOut;

public class ServerService implements NcsPacketListener<SimpleBytesNcsPacket>, Disposable {

    /**
     * Contains the OPCODES and {@link AbstractPacketIn} this {@link ClientService} will listen for.
     */
    private final PacketListener packetListener = new PacketListener();
    /**
     * The networking client for this service
     */
    private NcsServerService ncsServerService;

    /**
     * If the client service is initialized then this will become true.
     * This is mainly used to prevent {@link NullPointerException}.
     */
    private boolean initialized = false;

    /**
     * Initialize server networking systems
     *
     * @param port      The incoming connection port
     * @param secretKey The shared secret key
     */
    public void initializeServer(int port, String secretKey) {
        initializeServer(port, new GenericStateListener(), secretKey);
    }

    /**
     * Initialize server networking systems
     *
     * @param port             The incoming connection port
     * @param ncsStateListener A class that will report on the connection status
     * @param secretKey        The shared secret key
     */
    public void initializeServer(int port, NcsStateListener ncsStateListener, String secretKey) {
        if (initialized) throw new RuntimeException("This service has already been initialized!");
        ncsServerService = NcsServerHelper.createSimpleServer(port, this, ncsStateListener);
        ncsServerService.getConfiguration().setUseEpoll(true); // use optimized linux epoll transport

        // Setup packet obfuscation
        ncsServerService.getConfiguration().setTlsEnabled(true);
        ncsServerService.getConfiguration().setSharedSecret(secretKey);
        ncsServerService.getConfiguration().setUsePskOBF(true);
        ncsServerService.getConfiguration().setUsePskMac(true);

        // Finally, start networking service
        ncsServerService.startNow();
        initialized = true;
    }

    /**
     * Add inbound packet listeners
     *
     * @param abstractPacketIn Packet(s) we want to listen for
     */
    public void addPacketListeners(AbstractPacketIn... abstractPacketIn) {
        for (AbstractPacketIn packetIn : abstractPacketIn) {
            packetListener.addPacketListener(packetIn);
        }
    }

    /**
     * Sends a packet to the server.
     *
     * @param connection        Represents a connection to a peer
     * @param abstractPacketOut The packet we want to send.
     */
    public void sendAndFlushPacket(NcsConnection connection, AbstractPacketOut abstractPacketOut) {
        SimpleBytesNcsPacket packet = (SimpleBytesNcsPacket) ncsServerService.createPacket();

        packet.startEncoding();
        packet.encodeByte(abstractPacketOut.getPacketOpcode()); // Send opcode
        abstractPacketOut.createPacket(packet); // Send packet contents
        packet.finishEncoding();

        connection.sendAndFlush(packet);
    }

    /**
     * Called with received packet
     *
     * @param connection Represents a connection to a peer
     * @param packet     the packet
     */
    @Override
    public void onPacket(NcsConnection connection, SimpleBytesNcsPacket packet) {
        packet.startDecoding();
        byte opcode = packet.decodeByte(); // Get the incoming opcode
        packetListener.getPacketListener(opcode).processPacket(connection, packet);
        packet.finishDecoding();
    }

    @Override
    public void dispose() {
        if (!initialized) return;
        // Shut down networking
        ncsServerService.stopNow();
    }
}
