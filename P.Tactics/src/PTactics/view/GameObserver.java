package PTactics.view;

import PTactics.model.game.Game;

public interface GameObserver {
	void onPlayersUpdate(Game game);
	void onBoardUpdate(Game game);
	void onTroopAction(Game game);
	void onTroopSelection(Game game);
	void onNextTurn(Game game);
}
