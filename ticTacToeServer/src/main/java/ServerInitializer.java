import java.io.IOException;
import java.net.ServerSocket;
import java.text.ParseException;

public class ServerInitializer {

    private boolean serverRunning = false;
    private ServerSocket serverSocket = null;


    public ServerInitializer(String[] args)
    {
        serverRunning = setUpServer(args);
    }

    private boolean setUpServer(String[] args)
    {
        int portNumber = 8081;
        if (args.length != 1) {
            System.err.println("Usage: java TicTacToeServer <port number>");
//            System.exit(1);

        }
        else
            portNumber = Integer.parseInt(args[0]);

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println(String.format("TicTacToe server started at port: %d", portNumber));
        } catch (IOException e) {
            System.err.println(String.format("Initialization error. Message: %s, Details: %s",
                    e.getMessage(), e.getStackTrace()));
            return false;
        }

        return true;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isServerRunning() {
        return serverRunning;
    }
}
