import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonTest {

    @Test
    void commandEqual() {
        String msg = "Move 8";
        assertTrue(Common.commandEqual(msg, TicTacToeCommands.Move));

        msg = "MOVE 8";
        assertFalse(Common.commandEqual(msg, TicTacToeCommands.Move));

        msg = "Move -1";
        assertTrue(Common.commandEqual(msg, TicTacToeCommands.Move));

        msg = "Move 123";
        assertTrue(Common.commandEqual(msg, TicTacToeCommands.Move));

        msg = " Move 123";
        assertFalse(Common.commandEqual(msg, TicTacToeCommands.Move));
    }
}