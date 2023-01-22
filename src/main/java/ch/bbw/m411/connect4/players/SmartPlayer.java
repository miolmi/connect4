package ch.bbw.m411.connect4.players;

import ch.bbw.m411.connect4.Connect4ArenaMain;

import java.util.Arrays;
import java.util.Objects;

import static ch.bbw.m411.connect4.Const.*;

public class SmartPlayer extends DefaultPlayer{

    @Override
    int play() {
        BESTMOVE = -1;
        INITIALFREEFIELDS = (int) Arrays.stream(board)
                .filter(Objects::isNull)
                .count();
        var bestScore = Connect4ArenaMain.minimax(myColor, MAXDEPTH, INITIALFREEFIELDS, board);
        if (bestScore == 1) {
            System.out.println("I WILL WIN");
        } else if (bestScore <= 0) {
            System.out.println("ITS A DRAW");
        }
        return BESTMOVE;
    }

}
