package ch.bbw.m411.connect4;

import java.util.List;

import ch.bbw.m411.connect4.players.GreedyPlayer;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class Connect4MainTest implements WithAssertions {

	protected Connect4ArenaMain newInstance() {
		return new Connect4ArenaMain();
	}

	Stone[] fromString(String boardStr) {
		var board = boardStr.codePoints()
				.map(Character::toLowerCase)
				.filter(x -> List.of('x', 'o', '.')
						.contains((char) x))
				.mapToObj(x -> x == 'x' ? Stone.RED : (x == 'o' ? Stone.BLUE : null))
				.toArray(Stone[]::new);
		assertThat(board).hasSize(Const.WIDTH * Const.HEIGHT);
		return board;
	}

	AbstractBooleanAssert<?> assertThatXWin(String boardStr) {
		var board = fromString(boardStr);
		return assertThat(newInstance().isWinning(board, Stone.RED)).as(Connect4ArenaMain.toDebugString(board));
	}

/*	@Test
	void alphBetaShouldFinish() {
		var red = new Connect4AlphaBetaPlayer(3);
		var blue = new Connect4AlphaBetaPlayer(10);
		assertThat(() -> newInstance().play(red, blue)).
	}*/

	@Test
	void isWin() {
		assertThatXWin("xxxx... ....... ....... .......").isTrue();
		assertThatXWin(".xxxx.. ....... ....... .......").isTrue();
		assertThatXWin("..xxxx. ....... ....... .......").isTrue();
		assertThatXWin("...xxxx ....... ....... .......").isTrue();
		assertThatXWin("...x... ...x... ...x... ...x...").isTrue();
		assertThatXWin("......x ......x ......x ......x").isTrue();
		assertThatXWin("xooo... .xoo... ..xo... ...x...").isTrue();
		assertThatXWin(".ooxo.. .oxoo.. .xxxx.. .......").isTrue();
		assertThatXWin(".ooxo.x .oxoo.. .ooxx.. .xxxx..").isTrue();
		assertThatXWin("oooo... xxxx... ....... .......").isTrue();
	}

	@Test
	void noWin() {
		assertThatXWin("....... ....... ....... .......").isFalse();
		assertThatXWin("xxx.xx. ....... ....... .......").isFalse();
		assertThatXWin("xxx.xxx xxx.xxx xxx.xxx .......").isFalse();
		assertThatXWin("xx.x.xx xx.x.xx xx.x.xx .......").isFalse();
		assertThatXWin("ooo.ooo xxx.xxx xxx.xxx xxx.xxx").isFalse();
		assertThatXWin("oo.o.oo xx.x.xx xx.x.xx xx.x.xx").isFalse();
		assertThatXWin("oooo... ....... ....... .......").isFalse();
		assertThatXWin("xxx.xx. xxx.xx. xxx.... o......").isFalse();
		assertThatXWin("xxxo... x.x.... x.o.... o.x....").isFalse();
	}

	@Test
	void inAGreedyBattleTheFirstPlayerWillWin() {
		var red = new GreedyPlayer();
		var blue = new GreedyPlayer();
		assertThat(newInstance().play(red, blue)).isSameAs(red);
	}
}
