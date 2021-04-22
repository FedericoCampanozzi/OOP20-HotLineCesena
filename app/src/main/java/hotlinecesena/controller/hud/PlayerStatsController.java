package hotlinecesena.controller.hud;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.hud.PlayerStatsView;

/**
 * Controller of the {@code PlayerStatsView}.
 */
public interface PlayerStatsController extends Updatable{

	/**
	 * @return the {@code PlayerStatsView}.
	 */
	PlayerStatsView getPlayerStatsView();

}