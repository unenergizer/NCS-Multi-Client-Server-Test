package networking.packet.in;

import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import networking.packet.AbstractPacketBase;

public abstract class AbstractPacketIn extends AbstractPacketBase {

    public AbstractPacketIn(byte packetOpcode) {
        super(packetOpcode);
    }

    /**
     * Called when an incoming packet needs to be decoded.
     *
     * @param connection The source of the incoming packet.
     * @param packet     The actual packet that needs to be decoded.
     */
    public abstract void processPacket(NcsConnection connection, SimpleBytesNcsPacket packet);
}
