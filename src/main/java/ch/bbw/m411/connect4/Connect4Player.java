package ch.bbw.m411.connect4;

public interface Connect4Player {

    /**
     * Called before the game starts and guaranteed to only be called once per livetime of the player.
     *
     * @param board       the starting board, usually an empty board.
     * @param colorToPlay the color of this player
     */
    void initialize(Stone[] board, Stone colorToPlay);

    /**
     * Perform a next move, will only be called if the Game is not over yet.
     * Each player has to keep an internal state of the 4x7 board, wher the 0-index is on the bottom row.
     * The index-layout looks as:
     * <pre>
     * 30 31 32 33 34 35 36
     * 14 15 16 17 18 19 29
     *  7  8  9 10 11 12 13
     *  0  1  2  3  4  5  6
     * </pre>
     *
     * @param opponentPlayed the last index where the opponent played to (in range 0 - width*height exclusive)
     *                       or -1 if this is the first move.
     * @return an index to play to (in range 0 - width*height exclusive)
     */
    int play(int opponentPlayed);

}
