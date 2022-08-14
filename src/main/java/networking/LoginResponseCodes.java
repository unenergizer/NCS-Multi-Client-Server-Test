package networking;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginResponseCodes {
    AUTHENTICATION_SUCCESS((byte) 0, "Connection successful, Account authenticated!"),
    AUTHENTICATION_FAILURE((byte) 1, "Wrong username or password combination!"),
    ACCOUNT_SUSPENDED((byte) 2, "Your account is currently suspended"),
    ACCOUNT_BANNED((byte) 3, "Your account has been permanently banned!"),
    CLIENT_OUT_OF_DATE((byte) 4, "Client version is out of date!"),
    SERVER_IS_OFFLINE((byte) 5, "Server is offline");


    private final byte responseCode;
    private final String responseCodeMeaning;

    public static LoginResponseCodes getLoginResponseCode(byte enumIndex) {
        for (LoginResponseCodes responseCode : LoginResponseCodes.values()) {
            if ((byte) responseCode.ordinal() == enumIndex) return responseCode;
        }
        throw new RuntimeException("LoginResponseCodes type miss match! Byte Received: " + enumIndex);
    }

    public static byte getByte(LoginResponseCodes responseCode) {
        return (byte) responseCode.ordinal();
    }
}
