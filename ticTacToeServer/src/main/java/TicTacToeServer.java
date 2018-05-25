import java.io.IOException;
import java.net.ServerSocket;

public class TicTacToeServer {

    public static void main(String[] args) {

        ServerInitializer serverInitializer = new ServerInitializer(args);

        if (serverInitializer.isServerRunning()) {
            ServerSocket serverSocket = serverInitializer.getServerSocket();
            try {
                while (true) {
                    IGameController game = new TicTacToeGame();
                    game.startGame(serverSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
