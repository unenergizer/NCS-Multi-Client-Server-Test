package networking.packet.out;

import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import networking.packet.AbstractPacketBase;

public abstract class AbstractPacketOut extends AbstractPacketBase {

    public AbstractPacketOut(byte packetOpcode) {
        super(packetOpcode);
    }

    /**
     * Creates the packet contents.
     *
     * @param packet The class needed to encode the packet contents.
     */
    public abstract void createPacket(SimpleBytesNcsPacket packet);
}
