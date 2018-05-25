import java.io.*;
import java.net.Socket;

/**
 * Tic Tac Toe game player class.
 */
public class TicTacToePlayer extends Thread implements IPlayer {

    private IGameController game;
    private IPlayer opponent;
    private Socket socket;
    private char sign;
    private PrintWriter output;
    private BufferedReader input;

    public TicTacToePlayer(char sign) {
        this.sign = sign;
    }

    public TicTacToePlayer(IGameController game, Socket clientSocket) throws IOException {
        this.game = game;
        this.socket = clientSocket;

        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            if (!(game instanceof TicTacToeGame))
                return;
            String inputLine = input.readLine();
            if (Common.commandEqual(inputLine, TicTacToeCommands.Assign)) {
                this.sign = ((TicTacToeGame)game).getSymbol();
                output.println(TicTacToeCommands.YourSign + " " + sign);
                System.out.println(String.format("Assigned %c to player", this.sign));
            }

            printBoardCommand();

            if (sign == 'X') {
                output.println(TicTacToeCommands.BeginGame.toString());
                output.println(TicTacToeCommands.MakeMove.toString());
            }


            while ((inputLine = input.readLine()) != null) {
                if (Common.commandEqual(inputLine, TicTacToeCommands.Move)) {

                    int[] rowCol = getLocationFromCommand(inputLine);

                    if (game.movePlayerToLocation(this, rowCol[0], rowCol[1])) {

                        printBoardCommand();

                        if (((TicTacToeGame)game).isGameBoardContainsWinningSequence()) {
                            output.println(TicTacToeCommands.Win.toString());
                            return;
                        }
                        else if (((TicTacToeGame)game).isGameBoardFull()) {
                            output.println(TicTacToeCommands.Tie.toString());
                            return;
                        }
                    }
                    else {
                        output.println(TicTacToeCommands.NotAllowedMove.toString());
                        output.println(TicTacToeCommands.MakeMove.toString());
                    }
                }
                else if (Common.commandEqual(inputLine, TicTacToeCommands.EndGame))
                    return;
            }
        } catch (IOException e) {
             System.err.println("Player disconnected because of error: " + e.toString());
        }
        catch (UnsupportedOperationException e) {
            System.err.println(e.toString());
        }
        finally
        {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void opponentMoved(int row, int col) {
        if (game instanceof TicTacToeGame) {
            output.println(String.format("%s %c%d",
                    TicTacToeCommands.PlayerMoved.toString(), ((TicTacToeGame)game).translateRowInt(row), col));

            if (((TicTacToeGame) game).isGameBoardContainsWinningSequence()) {
                output.println(TicTacToeCommands.Defeated.toString());
                printBoardCommand();
            } else if (((TicTacToeGame) game).isGameBoardFull()) {
                output.println(TicTacToeCommands.Tie.toString());
            } else {
                printBoardCommand();
                output.println(TicTacToeCommands.MakeMove.toString());
            }
        }
    }

    /**
     * Gets location value from command with location
     * @param command Command with location
     * @return Location value.
     * @throws UnsupportedOperationException When command dont have location value.
     */
    public int[] getLocationFromCommand(String command) throws UnsupportedOperationException {
        int[] rowCol = new int[2];
        if (command.contains(TicTacToeCommands.Move.toString())) {
            char colChar = command
                    .substring(TicTacToeCommands.Move.toString().length() + 1,
                    TicTacToeCommands.Move.toString().length() + 2).charAt(0);
            int row = Integer.parseInt(command
                    .substring(TicTacToeCommands.Move.toString().length() + 2,
                            TicTacToeCommands.Move.toString().length() + 3));
            rowCol[0] = ((TicTacToeGame)(game)).translateRowChar(colChar);
            rowCol[1] = row;

            return rowCol;
        }
        else throw new UnsupportedOperationException("Expected Move command");
    }

    /**
     * Prints game board to connected client.
     */
    public void printBoardCommand() {
        output.println(TicTacToeCommands.PrintBoard + " " + ((TicTacToeGame)game).printBoard());
    }

    @Override
    public void joinToGame() {
        this.start();
    }

    @Override
    public void setOpponent(IPlayer opponent) {
        this.opponent = opponent;
    }

    @Override
    public IPlayer getOpponent() {
        return opponent;
    }

    @Override
    public String toString() {
        return String.valueOf(sign);
    }
}
