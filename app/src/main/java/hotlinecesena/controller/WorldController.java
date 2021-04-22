package hotlinecesena.controller;

import hotlinecesena.model.events.Subscriber;

public interface WorldController extends Subscriber {

	/**
	 * @return the player life time of the current match.
	 */
	int getPlayerLifeTime();

	/**
	 * @return the amount of kills.
	 */
	int getEnemyKilledByPlayer();

	/**
	 * @return the amount of ammunitions bag picked.
	 */
	int getTotalAmmoBag();

	/**
	 * @return the amount of medikit picked.
	 */
	int getTotalMedikit();

	/**
	 * @return the amount of bullets fired by player.
	 */
	int getTotalAmmoShootedByPlayer();

	/**
	 * @return whether the player has picked or not the brief case.
	 */
	boolean isPickBriefCase();

	/**
	 * @return how many times the player has switched weapon.
	 */
	int getTotalWeaponsChanged();

}