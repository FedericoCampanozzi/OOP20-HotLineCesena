package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Collection of collectible {@code items}.
 */
public enum CollectibleType implements Item {
	
	/**
	 * Money type, collectible through the {@code BriefCase}.
	 */
	MONEY(1000),
	
	/**
	 * Ammunition type for the shotgun.
	 */
	SHOTGUN_AMMO(21),
	
	/**
	 * Ammunition type for the rifle.
	 */
	RIFLE_AMMO(90),
	
	/**
	 * Ammunition type for the pistol.
	 */
	PISTOL_AMMO(30);
	
	private int maxStacks;

	/**
	 * Class constructor.
	 * @param maxStacks
	 * 				The max number of a specific type of items in the inventory.
	 */
	CollectibleType(int maxStacks) {
		this.maxStacks = maxStacks;
	}

	/**
	 * @return the action of the item when picked up.
	 * It's {@code null} for this kind of items.
	 */
	@Override
	public Consumer<Actor> usage() {
		return null;
	}

	/**
	 * @return the max number of a specific type of items in the inventory.
	 */
	@Override
	public int getMaxStacks() {
		return this.maxStacks;
	}

}
