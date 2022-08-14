package networking.packet;

public class Opcodes {

    /****************************************************************
     *** LOGIN PLAYER PACKETS ***************************************
     ***************************************************************/

    // GAME CLIENT -> LOGIN SERVER
    public static final byte PLAYER_CONNECT_REQUEST = 126;
    public static final byte PLAYER_CLIENT_VERSION = 125;
    public static final byte PLAYER_SELECT_SERVER = 124;

    // LOGIN SERVER -> GAME CLIENT
    public static final byte LOGIN_PLAYER_RESPONSE = 126;
    public static final byte GAME_SERVER_LIST = 125;
    public static final byte GAME_SERVER_INFO = 124;

    /****************************************************************
     *** LOGIN GAME SERVER PACKETS **********************************
     ***************************************************************/

    // GAME SERVER -> LOGIN SERVER
    public static final byte REGISTER_GAME_SERVER = 126;

    // LOGIN SERVER -> GAME SERVER
    public static final byte INITIALIZE_GAME_SERVER_FOR_CLIENT_PLAYERS = 126;

    /****************************************************************
     *** GAME SERVER & GAME CLIENT PACKETS **************************
     ***************************************************************/

    // GAME CLIENT <-> GAME SERVER
    public static final byte PING = 126;
    public static final byte TEST_MESSAGE_IN = 125;

    // GAME CLIENT -> GAME SERVER

    // GAME SERVER -> GAME CLIENT
}
