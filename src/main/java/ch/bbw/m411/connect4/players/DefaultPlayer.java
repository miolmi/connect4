package ch.bbw.m411.connect4.players;

import ch.bbw.m411.connect4.Connect4Player;
import ch.bbw.m411.connect4.Stone;

import static ch.bbw.m411.connect4.Const.NOMOVE;

public abstract class DefaultPlayer implements Connect4Player {

    Stone[] board;
    Stone myColor;

    @Override
    public void initialize(Stone[] board, Stone colorToPlay) {
        this.board = board;
        myColor = colorToPlay;
    }

    @Override
    public int play(int opponentPlayed) {
        if (opponentPlayed != NOMOVE) {
            board[opponentPlayed] = myColor.opponent();
        }
        var playTo = play();
        board[playTo] = myColor;
        return playTo;
    }

    /**
     * Givent the current {@link #board}, find a suitable position-index to play to.
     *
     * @return the position to play to as defined by {@link Connect4Player#play(int)}.
     */
    abstract int play();


}
