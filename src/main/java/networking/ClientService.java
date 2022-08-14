package networking;

import com.badlogic.gdx.utils.Disposable;
import com.github.terefang.ncs.client.NcsClientHelper;
import com.github.terefang.ncs.client.NcsClientService;
import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.NcsPacketListener;
import com.github.terefang.ncs.common.NcsStateListener;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import networking.packet.in.AbstractPacketIn;
import networking.packet.out.AbstractPacketOut;

public class ClientService implements NcsPacketListener<SimpleBytesNcsPacket>, Disposable {

    /**
     * Contains the OPCODES and {@link AbstractPacketIn} this {@link ClientService} will listen for.
     */
    private final PacketListener packetListener = new PacketListener();

    /**
     * The networking client for this service
     */
    private NcsClientService client;

    /**
     * If the client service is initialized then this will become true.
     * This is mainly used to prevent {@link NullPointerException}.
     */
    private boolean initialized = false;

    /**
     * Initialize client networking systems
     *
     * @param server    The address to connect to
     * @param port      The port to connect to
     * @param secretKey The shared secret key
     */
    public void initializeClient(String server, int port, String secretKey) {
        initializeClient(server, port, new GenericStateListener(), secretKey);
    }

    /**
     * Initialize client networking systems
     *
     * @param server           The address to connect to
     * @param port             The port to connect to
     * @param ncsStateListener A class that will report on the connection status
     * @param secretKey        The shared secret key
     */
    public void initializeClient(String server, int port, NcsStateListener ncsStateListener, String secretKey) {
        if (initialized) throw new RuntimeException("This service has already been initialized!");
        client = NcsClientHelper.createSimpleClient(server, port, this, ncsStateListener);
        client.getConfiguration().setSharedSecret(secretKey);
        client.getConfiguration().setTlsEnabled(true);
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
     * Attempt to connect this client to the server.
     */
    public void connectToServer() {
        if (isConnected()) return;
        System.out.println("Connecting to the server!");
        client.connectNow();
    }

    /**
     * Disconnect this client from the server.
     */
    public void disconnectFromServer() {
        if (!isConnected()) return;
        System.out.println("Disconnecting from the server!");
        client.disconnectNow();
    }

    /**
     * Sends a packet to the server.
     *
     * @param abstractPacketOut The packet we want to send.
     */
    public void sendAndFlushPacket(AbstractPacketOut abstractPacketOut) {
        SimpleBytesNcsPacket packet = (SimpleBytesNcsPacket) client.createPacket();

        packet.startEncoding();
        packet.encodeByte(abstractPacketOut.getPacketOpcode()); // Send opcode
        abstractPacketOut.createPacket(packet); // Send packet contents
        packet.finishEncoding();

        client.sendAndFlush(packet);
    }

    @Override
    public void onPacket(NcsConnection connection, SimpleBytesNcsPacket packet) {
        packet.startDecoding();
        byte opcode = packet.decodeByte(); // Get the incoming opcode
        packetListener.getPacketListener(opcode).processPacket(connection, packet);
        packet.finishDecoding();
    }

    /**
     * Checks if the client is connected to the server.
     *
     * @return True if connected, false otherwise
     */
    public boolean isConnected() {
        if (!initialized) return false;
        return client.isConnected();
    }

    @Override
    public void dispose() {
        if (!initialized) return;
        disconnectFromServer();
        client.shutdown();
    }
}
