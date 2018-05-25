import java.util.HashSet;

/**
 * Common class containing common static functions.
 */
public class Common {

    /**
     * Compare command from message with expected command.
     *
     * @param msg String message containing command received from input.
     * @param command Expected command.
     * @return True if command from message equals to expected command.
     */
    public static boolean commandEqual(String msg, TicTacToeCommands command)
    {
        if (msg.length() > 0 && msg.contains(command.toString()))
        {
            int endIndex = msg.contains(" ") ? msg.indexOf(" ") : msg.length();
            String received = msg.substring(0, endIndex);
            if (!getEnums().contains(received))
                return false;
            TicTacToeCommands receivedCommand = TicTacToeCommands.valueOf(received);
            return command == receivedCommand;
        }
        return false;
    }

    /**
     * Gets HashSet of TicTacToeCommands.
     *
     * @return HashSet of TicTacToeCommands.
     */
    public static HashSet<String> getEnums() {

        HashSet<String> values = new HashSet<String>();

        for (TicTacToeCommands c : TicTacToeCommands.values()) {
            values.add(c.name());
        }

        return values;
    }
}
