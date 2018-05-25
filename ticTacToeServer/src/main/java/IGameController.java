import java.io.IOException;
import java.net.ServerSocket;

/**
 * Simple game socket communicated game interface
 */
public interface IGameController {

    /**
     * Method starting the game.
     * @param socket Server socket reference.
     * @throws IOException When there is connection error.
     */
    void startGame(ServerSocket socket) throws IOException;

    /**
     * Method allows move to location by reference player.
     * @param player Moving player reference.
     * @param location Moving location.
     * @return True if move was allowed and finished, otherwise False.
     */
    boolean movePlayerToLocation(IPlayer player, int location);

    /**
     * Sets current game player.
     * @param player Player reference.
     */
    void setCurrentPlayer(IPlayer player);

}
