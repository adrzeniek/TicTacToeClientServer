import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToePlayerTest {

    @Test
    void getLocationFromCommand() {
        final TicTacToePlayer p = new TicTacToePlayer('X');
        int expected = 5;
        String fineCommand = new StringBuffer().append(TicTacToeCommands.Move).append(" ").append(expected).toString();
        assertEquals(expected, p.getLocationFromCommand(fineCommand));

        final String errCommand = new StringBuffer().append(TicTacToeCommands.Empty).toString();
        Executable e = new Executable() {
            @Override
            public void execute() throws Throwable {
                p.getLocationFromCommand(errCommand);
            }
        };
        assertThrows(UnsupportedOperationException.class, e);

    }
}