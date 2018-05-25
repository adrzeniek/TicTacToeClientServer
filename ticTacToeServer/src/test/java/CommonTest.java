import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CommonTest {

    @Test
    void commandEqual() {
        String msg = TicTacToeCommands.PlayerMoved + " 5";
        assertTrue(Common.commandEqual(msg, TicTacToeCommands.PlayerMoved));

        msg = "PLAYERMOVED 2";
        assertFalse(Common.commandEqual(msg, TicTacToeCommands.PlayerMoved));
    }
}