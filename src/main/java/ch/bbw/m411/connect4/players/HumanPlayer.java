package ch.bbw.m411.connect4.players;

import ch.bbw.m411.connect4.Stone;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ch.bbw.m411.connect4.Const.*;


public class HumanPlayer extends DefaultPlayer{
    public static String toPrettyString(Stone[] board) {
        var sb = new StringBuilder();
        for (int r = HEIGHT - 1; r >= 0; r--) {
            for (int c = 0; c < WIDTH; c++) {
                var index = r * WIDTH + c;
                if (board[index] == null) {
                    if (index < WIDTH || board[index - WIDTH] != null) {
                        sb.append("\033[37m" + index + "\033[0m ");
                        if (index < 10) {
                            sb.append(" ");
                        }
                    } else {
                        sb.append("\033[37m.\033[0m  ");
                    }
                } else if (board[index] == Stone.RED) {
                    sb.append("\033[1;31mX\033[0m  ");
                } else {
                    sb.append("\033[1;34mO\033[0m  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    int play() {
        System.out.println("where to to put the next " + myColor + "?");
        var scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        return Integer.parseInt(scanner.nextLine());
    }


}
