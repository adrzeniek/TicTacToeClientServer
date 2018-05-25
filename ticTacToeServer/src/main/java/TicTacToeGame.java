import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeGame implements IGameController {

    private char[] gameBoard = {'.', '.', '.', '.', '.', '.', '.', '.', '.'};

    private IPlayer currentPlayer;

    public TicTacToeGame() {    }

    @Override
    public void startGame(ServerSocket serverSocket) throws IOException {

        System.out.println("Waiting for players");

        Socket player1Socket = serverSocket.accept();
        IPlayer player1 = new TicTacToePlayer(this, player1Socket);

        Socket player2Socket = serverSocket.accept();
        IPlayer player2 = new TicTacToePlayer(this, player2Socket);

        System.out.println("Setting opponents to each player");
        player1.setOpponent(player2);
        player2.setOpponent(player1);

        System.out.println("Beginning the game!");

        setCurrentPlayer(player1);

        player1.joinToGame();
        player2.joinToGame();
    }

    private char lastAssigned = 'O';
    public synchronized char getSymbol() {
        if (lastAssigned == 'O')
        {
            lastAssigned = 'X';
            return lastAssigned;
        }
        else
        {
            lastAssigned = 'O';
            return lastAssigned;
        }
    }

    @Override
    public boolean movePlayerToLocation(IPlayer player, int location) {
        return move(player, location);
    }

    @Override
    public void setCurrentPlayer(IPlayer player) {
        currentPlayer = player;
    }

    public boolean isMoveAllowed(int location, IPlayer player) {
        if (location < 0 || location > gameBoard.length - 1)
            return false;

        return currentPlayer == player && gameBoard[location] == '.';
    }

    private synchronized boolean move(IPlayer player, int location) {
        if (isMoveAllowed(location, player)) {
            char playerSign =  player.toString().charAt(0);
            gameBoard[location] = playerSign;

            currentPlayer = player.getOpponent();
            currentPlayer.opponentMoved(location);
            return true;
        }
        return false;
    }

    public boolean isGameBoardFull() {
        for (char ch : gameBoard) {
            if (ch == '.')
                return false;
        }
        return true;
    }

    public boolean isGameBoardContainsWinningSequence() {

        return  (gameBoard[0] != '.' && gameBoard[1] == gameBoard[0] && gameBoard[2] == gameBoard[0]) ||
                (gameBoard[0] != '.' && gameBoard[4] == gameBoard[0] && gameBoard[8] == gameBoard[0]) ||
                (gameBoard[0] != '.' && gameBoard[3] == gameBoard[0] && gameBoard[6] == gameBoard[0]) ||
                (gameBoard[1] != '.' && gameBoard[4] == gameBoard[1] && gameBoard[7] == gameBoard[1]) ||
                (gameBoard[2] != '.' && gameBoard[4] == gameBoard[2] && gameBoard[6] == gameBoard[2]) ||
                (gameBoard[2] != '.' && gameBoard[5] == gameBoard[2] && gameBoard[8] == gameBoard[2]) ||
                (gameBoard[5] != '.' && gameBoard[4] == gameBoard[5] && gameBoard[3] == gameBoard[5]) ||
                (gameBoard[8] != '.' && gameBoard[7] == gameBoard[8] && gameBoard[6] == gameBoard[8]);
    }

    public char[] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(char[] board) {
        gameBoard = board;
    }

    public String printBoard() {
        StringBuffer sb = new StringBuffer();

        int j = 0;
        for (int i = 0; i < gameBoard.length; i++)
        {
            sb.append(gameBoard[i]);
            j++;
            if (j == 3 && i != gameBoard.length - 1) {
                sb.append('|');
                j = 0;
            }
        }
        return sb.toString();
    }
}
