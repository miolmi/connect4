package ch.bbw.m411.connect4;

import ch.bbw.m411.connect4.players.GreedyPlayer;
import ch.bbw.m411.connect4.players.HumanPlayer;
import ch.bbw.m411.connect4.players.SmartPlayer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import static ch.bbw.m411.connect4.Const.*;

/**
 * Plays a game of Connect Four on a 4x7 board (a variation of the original 6x7 board).
 * The pieces fall straight down, occupying the lowest available space within the column.
 */
public class Connect4ArenaMain {
	public static void main(String[] args) {
		// choose which players should play against each other
		new Connect4ArenaMain().play(new HumanPlayer(), new SmartPlayer());
	}

	static String toDebugString(Stone[] board) {
		var sb = new StringBuilder();
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				var value = board[r * WIDTH + c];
				sb.append(value == null ? "." : (value == Stone.RED ? "X" : "O"));
			}
			sb.append("-");
		}
		return sb.toString();
	}

	Connect4Player play(Connect4Player red, Connect4Player blue) {
		if (red == blue) {
			throw new IllegalStateException("must be different players (simply create two instances)");
		}
		var board = new Stone[WIDTH * HEIGHT];
		red.initialize(Arrays.copyOf(board, board.length), Stone.RED);
		blue.initialize(Arrays.copyOf(board, board.length), Stone.BLUE);
		var lastMove = NOMOVE;
		var currentPlayer = red;
		for (int round = 0; round < board.length; round++) {
			var currentColor = currentPlayer == red ? Stone.RED : Stone.BLUE;
			System.out.println(HumanPlayer.toPrettyString(board) + currentColor + " to play next...");
			lastMove = currentPlayer.play(lastMove);
			if (lastMove < 0 || lastMove >= WIDTH * HEIGHT ||
					board[lastMove] != null && (lastMove < WIDTH || board[lastMove - WIDTH] != null)) {
				throw new IllegalStateException("cannot play to position " + lastMove + " @ " + toDebugString(board));
			}
			board[lastMove] = currentColor;
			if (isWinning(board, currentColor)) {
				System.out.println(
						HumanPlayer.toPrettyString(board) + "...and the winner is: " + currentColor + " @ " + toDebugString(board));
				return currentPlayer;
			}
			currentPlayer = currentPlayer == red ? blue : red;
		}
		System.out.println(HumanPlayer.toPrettyString(board) + "...it's a DRAW @ " + toDebugString(board));
		return null; // null implies a draw
	}

	static boolean isWinning(Stone[] board, Stone forColor) {
		// checks for vertical lines
		for (int i = 0; i <= 6; i++) {
			if (board[i] == forColor && board[i + 7] == forColor && board[i + (7 * 2)] == forColor && board[i + (7 * 3)] == forColor) {
				return true;
			}
		}
		// checks for horizontal lines
		for (int i = 0; i < 28; i += 7) {
			for (int x = 0; x < 4; x++) {
				if (board[x + i] == forColor && board[(x + 1) + i] == forColor && board[(x + 2) + i] == forColor && board[(x + 3) + i] == forColor) {
					return true;
				}
			}
		}
		// checks for diagonal / lines
		for (int i = 0; i < 4; i++) {
			if (board[i] == forColor && board[i + 8] == forColor && board[i + 16] == forColor && board[i + 24] == forColor) {
				return true;
			}
		}
		// checks for diagonal \ lines
		for (int i = 6; i > 2; i--) {
			if (board[i] == forColor && board[i + 6] == forColor && board[i + 12] == forColor && board[i + 18] == forColor) {
				return true;
			}
		}
		return false;
	}

	public static long minimax(Stone currentPosition, int depth, int freeFields, Stone[] board) {
		// looks if somebody won
		if (isWinning(board, currentPosition.opponent())) {
			return -1000;
		}
		// looks if board is full
		if (freeFields == 0) {
			return 0;
		}
		// evaluates risk for the next play
		if (depth == 0) {
			var myCounter = evaluate(board, currentPosition);
			var opponentCounter = evaluate(board, currentPosition.opponent());

			return myCounter - opponentCounter;
		}

		long bestValue = -100000;

		// goes only though playable fields
		for (var i : getPlayableMoves(board)) {

			board[i] = currentPosition; // play a stone
			var currentValue = -minimax(currentPosition.opponent(), depth - 1, freeFields - 1, board);
			board[i] = null; // revert the last move
			if (depth == MAXDEPTH) {
				System.out.println(currentValue + " pos:" + i);
			}
			if (currentValue > bestValue) {
				bestValue = currentValue;
				if (depth == MAXDEPTH) {
					BESTMOVE = i; // a bit of a hack: we have to return a position (not a score)
				}
			}
		}
		return bestValue;
	}

	private static int evaluate(Stone[] board, Stone myColor) {
		var counterValue = 0;
		var counter = 0;

		for (var stone : board) {
			counter++;
			if (stone == myColor) {
				// low risk
				if (counter == 0 || counter == 7 || counter == 14 || counter == 21 ||
						counter == 6 || counter == 13 || counter == 20 || counter == 27) {
					counterValue += 3;
				}
				// mid risk
				if (counter == 1 || counter == 8 || counter == 15 || counter == 22 ||
						counter == 5 || counter == 12 || counter == 19 || counter == 26) {
					counterValue += 4;
				}
				// high risk
				if (counter == 2 || counter == 9 || counter == 16 || counter == 23 ||
						counter == 4 || counter == 11 || counter == 18 || counter == 25) {
					counterValue += 5;
				}
				// no chance risk
				if (counter == 3 || counter == 10 || counter == 17 || counter == 24) {
					counterValue += 7;
				}
			}
		}
		return counterValue;
	}


	public static int[] getPlayableMoves(Stone[] board) {
		//a list with the size of 7
		var possiblePlays = new int[7];

		//the next possible playable field
		var nextPossibleField = 0;


		for (int i = 0; i < board.length; i++) {
			//checks if the board is full, so it just continues
			if (nextPossibleField > 6) {
				continue;
			}
			if (i <= 6) {
				if (board[i] == null) {
					possiblePlays[nextPossibleField] = i;
					nextPossibleField++;
				}
			}

			if (i > 6) {
				//checks if the position i is playable
				if (board[i] == null) {
					//checks under the position i,
					//if a stone is present
					if (board[i - 7] != null) {
						possiblePlays[nextPossibleField] = i;
						nextPossibleField++;
					}
				}
			}
		}

		return possiblePlays;
	}
}






/*

		int play(int opponentPlayed);
	}

	*/
/**
	 * An abstract helper class to keep track of a board (and whatever we or the opponent played).
	 *//*

	public abstract static class DefaultPlayer implements Connect4Player {

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

		*/
/**
		 * Givent the current {@link #board}, find a suitable position-index to play to.
		 * @return the position to play to as defined by {@link Connect4Player#play(int)}.
		 *//*

		abstract int play();

	}

	public static class HumanPlayer extends DefaultPlayer {

		static String toPrettyString(Stone[] board) {
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


	public static class GreedyPlayer extends DefaultPlayer {

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
			throw new IllegalStateException("cannot play at all");
		}
	}

*/
