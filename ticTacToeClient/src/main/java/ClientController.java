import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientController {

    private Socket socket;
    private char assignedSign;
    private BufferedReader input;
    private PrintWriter output;

    private ClientView view;

    public ClientController(String[] args, ClientView view) throws IOException {
        String hostname = "localhost";
        int port = 8081;
        if (args.length > 0)
            hostname = args[0];
        if (args.length > 1)
            port = Integer.parseInt(args[1]);

        socket = new Socket(hostname, port);

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(),true);

        this.view = view;
    }

    /**
     * Method start tictactoe client game loop.
     */
    public void startGame() {

        askForSign();
        receivePlayerSign();

        while(true) {
            String message = receiveMessage();

            if (Common.commandEqual(message, TicTacToeCommands.BeginGame))
            {
                view.printBeginGame();
            }
            else if (Common.commandEqual(message, TicTacToeCommands.PlayerMoved)) {

                String loc = message.substring(TicTacToeCommands.PlayerMoved.toString().length() + 1);
                view.printOpponentMoved(loc);
            }
            else if (Common.commandEqual(message, TicTacToeCommands.PrintBoard)) {
                view.printGameBoard(message.
                        substring(TicTacToeCommands.PrintBoard.toString().length() + 1));
            }
            else if (Common.commandEqual(message, TicTacToeCommands.NotAllowedMove)) {
                view.printMoveNotAllowed();
            }
            else if (Common.commandEqual(message, TicTacToeCommands.Tie))
            {
                view.printTie();
                break;
            }
            else if (Common.commandEqual(message, TicTacToeCommands.Win)) {
                view.printWin();
                break;
            }
            else if (Common.commandEqual(message, TicTacToeCommands.Defeated))
            {
                view.printDefeated();
                break;
            }
            else if (Common.commandEqual(message, TicTacToeCommands.EndGame)) {
                view.printEndGame();
                break;
            }
            else if (Common.commandEqual(message, TicTacToeCommands.MakeMove))
            {
                inputLocation();
            }
        }

        output.println(TicTacToeCommands.EndGame);
    }

    private void inputLocation()
    {
        String location = view.readLocation();
        output.println(TicTacToeCommands.Move + " " + location);
    }

    private void askForSign()
    {
        output.println(TicTacToeCommands.Assign);
    }

    private void receivePlayerSign() {
        String msg = receiveMessage();
        if (Common.commandEqual(msg, TicTacToeCommands.YourSign)) {
            assignedSign = msg.substring(TicTacToeCommands.YourSign.toString().length() + 1).charAt(0);
            System.out.println("Player received game sign " + assignedSign);
        }
    }

    /**
     * Method to close socket connection.
     * @throws IOException When socket close finished with error.
     */
    public void close() throws IOException {
        socket.close();
    }

    private String receiveMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            view.printReceivedMessageError(e);
            return TicTacToeCommands.EndGame.toString();
        }
    }
}
