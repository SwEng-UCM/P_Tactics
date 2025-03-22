package PTactics.view;

import PTactics.model.game.BoardInterface;
import PTactics.model.game.Game;

public interface GameObserver {
	void onPlayersUpdate(Game game);
	void onBoardUpdate(BoardInterface board);
	void onTroopAction(Game game); // maybe this une is useless -> on every troop action either the board or the players are updated
	void onTroopSelection(Game game);
	void onNextTurn(Game game);
}
