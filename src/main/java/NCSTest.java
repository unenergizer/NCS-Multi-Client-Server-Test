import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.NcsStateListener;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import networking.ClientService;
import networking.ServerService;

public class NCSTest {
    private static final String IP = "127.0.0.1";
    private static final String SECRET_KEY = "07cwI&Y4gLXtJrQdfYWcKey!cseY9jB0Q*bveiT$zi6LX7%xMuGm!hzW%rQj%8Wf";

    private static final int PORT_1 = 3001;
    private static final int PORT_2 = 3002;
    private static final int PORT_3 = 3003;
    private static final int PORT_4 = 3004;

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("Setting up server(s)...");

        ServerService serverService1 = new ServerService();
        ServerService serverService2 = new ServerService();
        ServerService serverService3 = new ServerService();
        ServerService serverService4 = new ServerService();

        serverService1.initializeServer(PORT_1, SECRET_KEY);
//        serverService2.initializeServer(PORT_2, SECRET_KEY);
//        serverService3.initializeServer(PORT_3, SECRET_KEY);
//        serverService4.initializeServer(PORT_4, SECRET_KEY);

        System.out.println("Sleeping... #1");
        Thread.sleep(3000);


        System.out.println("Setting up clients(s)...");

        ClientService clientService1 = new ClientService();
        ClientService clientService2 = new ClientService();
        ClientService clientService3 = new ClientService();
        ClientService clientService4 = new ClientService();

        clientService1.initializeClient(IP, PORT_1, new NCSListenerTest("1"), SECRET_KEY);
//        clientService2.initializeClient(IP, PORT_2, new NCSListenerTest("2"), SECRET_KEY);
//        clientService3.initializeClient(IP, PORT_3, new NCSListenerTest("3"), SECRET_KEY);
//        clientService4.initializeClient(IP, PORT_4, new NCSListenerTest("4"), SECRET_KEY);


        System.out.println("Sleeping... #2");
        Thread.sleep(3000);

        System.out.println("==== Testing Connections ===================");
        System.out.println("Connection 1: " + clientService1.isConnected());
//        System.out.println("Connection 2: " + clientService2.isConnected());
//        System.out.println("Connection 3: " + clientService3.isConnected());
//        System.out.println("Connection 4: " + clientService4.isConnected());

        Thread.sleep(3000);
        System.exit(0);
    }

    @AllArgsConstructor
    static class NCSListenerTest implements NcsStateListener {

        private final String connectionNumber;

        @Override
        public void onConnect(NcsConnection _connection) {
            System.out.println("Connection " + connectionNumber + " connected!");
        }

        @Override
        public void onDisconnect(NcsConnection _connection) {
            System.out.println("Connection " + connectionNumber + " disconnected!");
        }

        @Override
        public void onError(NcsConnection _connection, Throwable _cause) {
            System.out.println("Connection " + connectionNumber + " had an error!");
        }
    }
}
