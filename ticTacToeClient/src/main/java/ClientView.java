import java.util.Scanner;

public class ClientView {

    private final Scanner input;

    public ClientView()
    {
        input = new Scanner(System.in);
    }

    public void printOpponentMoved(String loc) {
        System.out.println(String.format("Opponent moved to location %s", loc));
    }

    public void printBeginGame() {
        System.out.println("You are beginning the game. Provide location [A-C][1-3] eg. A2");
    }

    public void printGameBoard(String gameBoard) {

        StringBuffer sb = new StringBuffer().append("  123").append('\n');
        sb.append("A ").append(gameBoard.substring(0,3)).append('\n');
        sb.append("B ").append(gameBoard.substring(4,7)).append('\n');
        sb.append("C ").append(gameBoard.substring(8,11)).append('\n');

        System.out.println(sb.toString());
    }

    public String readLocation() {
        return input.next();
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
