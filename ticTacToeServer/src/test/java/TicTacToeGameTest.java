import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGameTest {

    static TicTacToeGame game = null;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        game = new TicTacToeGame();
    }

    @org.junit.jupiter.api.Test
    void testIfIsGameBoardFull() {
        game.setGameBoard(new char[] {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'});
        assertTrue(game.isGameBoardFull());

        game.setGameBoard(new char[] {'X', 'X', 'X', 'X', 'X', 'X', 'X', '.', 'X'});
        assertFalse(game.isGameBoardFull());
    }

    @org.junit.jupiter.api.Test
    void testIfIsGameBoardContainsWinningSequence() {
        game.setGameBoard(new char[] { 'X', 'X', 'X', '.', '.', '.', '.', '.', '.'});
        assertTrue(game.isGameBoardContainsWinningSequence());

        game.setGameBoard(new char[] { 'X', 'X', 'O', '.', '.', '.', '.', '.', '.'});
        assertFalse(game.isGameBoardContainsWinningSequence());
    }

    @org.junit.jupiter.api.Test
    void testPrintEmptyBoard() {
        String boardPrint = game.printBoard();
        String expected = "...|...|...";
        assertEquals(expected.length(), boardPrint.length());

        assertTrue(boardPrint.equals(expected));
    }

    @org.junit.jupiter.api.Test
    void testIfIsMoveAllowed() {
        game.setGameBoard(new char[] { 'X', 'X', 'X', '.', '.', '.', '.', '.', '.'});

        IPlayer p = null;
        p = new TicTacToePlayer('X');
        game.setCurrentPlayer(p);
        assertNotNull(p);
        assertFalse(game.isMoveAllowed(2, p));
        assertTrue(game.isMoveAllowed(3, p));
        assertFalse(game.isMoveAllowed(-1, p));
        assertFalse(game.isMoveAllowed(game.getGameBoard().length + 22, p));
    }

}