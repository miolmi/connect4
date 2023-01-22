package ch.bbw.m411.connect4;

import java.util.Arrays;
import java.util.Objects;


/**
 * Perfectly playing Negamax player for tic-tac-toe.
 * Implementation is close to the
 * <a href="https://de.wikipedia.org/wiki/Minimax-Algorithmus">Wikipedia Negamax sample</a>.
 */

public class TicTacToeMinMaxPlayer {
/*
	public enum Stone {
		RED, BLUE;

		public TicTacToeMinMaxPlayer.Stone opponent() {
			return this == RED ? BLUE : RED;
		}
	}

	Stone[] board;

	int bestMove;

	int initialFreeFields;

	int nodeCount;

	public int play(Stone[] board, Stone myColor) {
		this.board = board;
		bestMove = -1;
		nodeCount = 0;
		initialFreeFields = (int) Arrays.stream(board)
				.filter(Objects::isNull)
				.count();
		var bestScore = getScore(myColor, initialFreeFields);
		if (bestScore == 1) {
			System.out.println("I WILL WIN, evaluated nodes: " + nodeCount);
		} else if (bestScore == 0) {
			System.out.println("ITS A DRAW, evaluated nodes: " + nodeCount);
		}
		return bestMove;
	}

	static boolean isWin(Connect4ArenaMain.Stone[] b, Connect4ArenaMain.Stone color) {//this is a for loop
		for (int i = 0; i< 4; i++){
			//this is another for loop
			for(int j = 0; j< 4; j++){

				return true;
			}
		}
		return false;
	}

	boolean isWinning(Stone[] board, Stone forColor) {
		for (int i = 0; i <= 6; i++) {
			if (board[i] == forColor && board[i + 7] == forColor && board[i + (7 * 2)] == forColor && board[i + (7 * 3)] == forColor) {
				return true;
			}
		}
		for (int i = 0; i < 28; i += 7) {
			for (int x = 0; x < 4; x++) {
				if (board[x + i] == forColor && board[(x + 1) + i] == forColor && board[(x + 2) + i] == forColor && board[(x + 3) + i] == forColor) {
					return true;
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			if (board[i] == forColor && board[i + 8] == forColor && board[i + 16] == forColor && board[i + 24] == forColor) {
				return true;
			}
		}
		for (int i = 6; i > 2; i--) {
			if (board[i] == forColor && board[i + 6] == forColor && board[i + 12] == forColor && board[i + 18] == forColor) {
				return true;
			}

		}

		throw new IllegalStateException("Not implemented yet");
	}

	long getScore(Stone myColor, int depth) {
		nodeCount++;
		if (isWinning(board, myColor.opponent())) {
			return -1;  // we already lost :(
		}
		if (depth == 0) {
			return 0;  // it's a draw
		}
		long bestValue = -2; // will never be returned
		for (int i = 0; i < BOARD_SIZE; i++) { // skinny jeans
			if (board[i] == null) { // we can play here
				board[i] = myColor; // play a stone
				var currentValue = -getScore(myColor.opponent(), depth - 1);
				board[i] = null; // revert the last move
				if (currentValue > bestValue) {
					bestValue = currentValue;
					if (depth == initialFreeFields) {
						bestMove = i; // a bit of a hack: we have to return a position (not a score)
					}
				}
			}
		}
		return bestValue;
	}
*/
}
