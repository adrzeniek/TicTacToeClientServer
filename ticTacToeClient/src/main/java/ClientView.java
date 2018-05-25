import java.util.Scanner;

public class ClientView {

    private final Scanner input;

    public ClientView()
    {
        input = new Scanner(System.in);
    }

    public void printOpponentMoved(int opponentLocation) {
        System.out.println(String.format("Opponent moved to location %d", opponentLocation));
    }

    public void printBeginGame() {
        System.out.println("You are beginning the game. Provide location [0 - 8]");
    }

    public void printGameBoard(String gameBoard) {
        String s = gameBoard.replace('|', '\n');
        System.out.println(s);
    }

    public int readLocation() {
        return input.nextInt();
    }

    public void printMoveNotAllowed()
    {
        System.out.println("Move not allowed. Try again...");
    }

    public void printReceivedMessageError(Exception e) {
        System.out.println("Error during message receiving. Error: " + e.getMessage());
    }

    public void printWin() {
        System.out.println("You win");
    }

    public void printTie() {
        System.out.println("Game finished with tie");
    }

    public void printDefeated() {
        System.out.println("You are defeated!");
    }

    public void printEndGame() {
        System.out.println("You has been disconnected from server!");
    }
}
