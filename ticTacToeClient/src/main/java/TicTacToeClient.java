import java.io.IOException;

public class TicTacToeClient {
    public static void main(String[] args) throws IOException {

        ClientView view = new ClientView();
        ClientController clientController = null;

        try {
            clientController = new ClientController(args, view);
            clientController.startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (clientController != null)
            {
                clientController.close();
            }
        }
    }
}
