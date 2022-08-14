package networking;

import networking.packet.in.AbstractPacketIn;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will map opcodes to packets.
 */
public class PacketListener {

    /**
     * A {@link Map} that contains an OPCODE key and a Packet value.
     * These are the opcodes we will listen for and the packet code
     * that will deconstruct them.
     */
    private final Map<Byte, AbstractPacketIn> abstractPacketMap = new HashMap<>();

    /**
     * Registers an incoming packet listener.
     *
     * @param abstractPacketIn The class that will listen for a specific packet.
     */
    public void addPacketListener(AbstractPacketIn abstractPacketIn) {
        abstractPacketMap.put(abstractPacketIn.getPacketOpcode(), abstractPacketIn);
    }

    /**
     * Returns an {@link AbstractPacketIn} for the provided opcode, if it exists.
     *
     * @param opcode The opcode that is coming from the client machine.
     * @return A {@link AbstractPacketIn} to be processed by the server.
     */
    public AbstractPacketIn getPacketListener(byte opcode) {
        if (abstractPacketMap.containsKey(opcode)) return abstractPacketMap.get(opcode);

        // No packet listener has been added for this opcode
        System.out.println("ERROR: Has this opcode been registered? Opcode: " + opcode);
        return null;
    }
}
