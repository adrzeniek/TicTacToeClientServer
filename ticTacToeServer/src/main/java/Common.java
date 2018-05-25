/**
 * Common utils methods class.
 */
public class Common {

    /**
     * Compares command in message to expected TicTacToeCommand.
     * @param msg Received command.
     * @param command Expected command.
     * @return True if command from message equals to expected command.
     */
    public static boolean commandEqual(String msg, TicTacToeCommands command)
    {
        String comAsString = command.toString();
        if (msg.length() > 0 && msg.contains(comAsString))
        {
            int endIndex = msg.contains(" ") ? msg.indexOf(" ") : msg.length();
            TicTacToeCommands receivedCommand = TicTacToeCommands
                    .valueOf(msg.substring(0, endIndex));
            return command == receivedCommand;
        }
        return false;
    }
}
