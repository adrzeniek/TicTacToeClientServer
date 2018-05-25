public interface IPlayer {

    /**
     * Sets the opponent player for this player;
     * @param opponent Opponent player reference;
     */
    void setOpponent(IPlayer opponent);


    /**
     * Gets the opponent player for this player.
     * @return Opponent player
     */
    IPlayer getOpponent();

    /**
     * Enable connected player to join game.
     */
    void joinToGame();

    /**
     * Method used to take action when opponent player has moved to location.
     * @param location Location where opponent player has moved.
     */
    void opponentMoved(int location);

}
