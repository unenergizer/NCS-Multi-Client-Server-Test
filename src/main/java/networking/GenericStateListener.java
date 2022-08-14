package networking;

import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.NcsStateListener;



public class GenericStateListener implements NcsStateListener {
    @Override
    public void onConnect(NcsConnection connection) {
        System.out.println("CONNECT: " + connection.getPeer().asString());
    }

    @Override
    public void onDisconnect(NcsConnection connection) {
        System.out.println("DISCONNECT: " + connection.getPeer().asString());
    }

    @Override
    public void onError(NcsConnection connection, Throwable cause) {
        System.out.println("ERROR: " + connection.getPeer().asString() + " -- " + cause.getMessage());
    }
}
