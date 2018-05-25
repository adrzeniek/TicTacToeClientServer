import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeGame implements IGameController {

    private char[][] gameBoard = {{'.', '.', '.'}, {'.', '.', '.'}, {'.', '.', '.'}};

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
    public boolean movePlayerToLocation(IPlayer player, int row, int col) {
        return move(player, row, col);
    }

    @Override
    public void setCurrentPlayer(IPlayer player) {
        currentPlayer = player;
    }

    public boolean isMoveAllowed(int row, int col, IPlayer player) {
        if (row >= 1 && row <= 3 && col >= 1 && col <= 3) {
            return currentPlayer == player && gameBoard[row - 1][col - 1] == '.';
        }
        return false;
    }

    public int translateRowChar(char rowChar)
    {
        switch (rowChar) {
            case 'A': return 1;
            case 'B': return 2;
            case 'C': return 3;
            default: return 0;
        }
    }

    public char translateRowInt(int rowInt)
    {
        switch (rowInt) {
            case 1: return 'A';
            case 2: return 'B';
            case 3: return 'C';
            default: return 0;
        }
    }

    private synchronized boolean move(IPlayer player, int row, int col) {
        if (isMoveAllowed(row, col, player)) {
            char playerSign =  player.toString().charAt(0);
            gameBoard[row - 1][col - 1] = playerSign;

            currentPlayer = player.getOpponent();
            currentPlayer.opponentMoved(row, col);
            return true;
        }
        return false;
    }

    public boolean isGameBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameBoard[row][col] == '.')
                    return false;
            }
        }
        return true;
    }

    public boolean isGameBoardContainsWinningSequence() {

        return  (gameBoard[0][0] != '.' && gameBoard[0][1] == gameBoard[0][0] && gameBoard[0][2] == gameBoard[0][0]) ||
                (gameBoard[1][0] != '.' && gameBoard[1][1] == gameBoard[1][0] && gameBoard[1][2] == gameBoard[1][0]) ||
                (gameBoard[2][0] != '.' && gameBoard[2][1] == gameBoard[2][0] && gameBoard[2][2] == gameBoard[2][0]) ||
                (gameBoard[0][0] != '.' && gameBoard[1][0] == gameBoard[0][0] && gameBoard[2][0] == gameBoard[0][0]) ||
                (gameBoard[0][1] != '.' && gameBoard[1][1] == gameBoard[0][1] && gameBoard[2][1] == gameBoard[0][1]) ||
                (gameBoard[0][2] != '.' && gameBoard[2][1] == gameBoard[0][2] && gameBoard[2][2] == gameBoard[0][2]) ||
                (gameBoard[0][0] != '.' && gameBoard[1][1] == gameBoard[0][0] && gameBoard[2][2] == gameBoard[0][0]) ||
                (gameBoard[0][2] != '.' && gameBoard[1][1] == gameBoard[0][2] && gameBoard[2][0] == gameBoard[0][2]);
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(char[][] board) {
        gameBoard = board;
    }

    public String printBoard() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++) {
                sb.append(gameBoard[i][j]);
            }
            if (i < 2)
                sb.append('|');
        }
        return sb.toString();
    }
}
