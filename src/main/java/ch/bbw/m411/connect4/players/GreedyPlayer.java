package ch.bbw.m411.connect4.players;

import static ch.bbw.m411.connect4.Const.*;

public class GreedyPlayer extends DefaultPlayer {

    @Override
    int play() {
        for (int c = 0; c < WIDTH; c++) {
            for (int r = 0; r < HEIGHT; r++) {
                var index = r * WIDTH + c;
                if (board[index] == null) {
                    return index;
                }
            }
        }
        throw new IllegalStateException("can't play");
    }


}
