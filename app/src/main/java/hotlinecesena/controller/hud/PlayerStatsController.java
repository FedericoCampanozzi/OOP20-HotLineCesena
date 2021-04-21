package hotlinecesena.controller.hud;

import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.view.hud.PlayerStatsView;

public interface PlayerStatsController extends Updatable{

	/**
	 * Update all the stats.
	 */
	Consumer<Double> getUpdateMethod();

	/**
	 * @return the {@code PlayerStatsView}.
	 */
	PlayerStatsView getPlayerStatsView();

}