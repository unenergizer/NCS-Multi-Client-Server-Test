package networking.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class AbstractPacketBase {

    /**
     * The networking opcode that represents this particular packet.
     */
    @Getter
    private final byte packetOpcode;
}
