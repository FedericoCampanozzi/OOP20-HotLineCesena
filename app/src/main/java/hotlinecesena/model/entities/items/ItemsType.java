package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Collection of basic items.
 */
public enum ItemsType implements Item {
	
	/**
	 * Heal actor
	 */
	MEDIKIT(actor -> actor.heal(50), 5),
	
	/**
	 * Add ammunition to the inventory of the actor.
	 */
	AMMO_BAG(actor -> {
		actor.getInventory().add(CollectibleType.SHOTGUN_AMMO, CollectibleType.SHOTGUN_AMMO.getMaxStacks() / 3);
		actor.getInventory().add(CollectibleType.RIFLE_AMMO, CollectibleType.RIFLE_AMMO.getMaxStacks() / 3);
		actor.getInventory().add(CollectibleType.PISTOL_AMMO, CollectibleType.PISTOL_AMMO.getMaxStacks() / 3);
		}, 5),
	
	/**
	 * Add money to the inventory of the actor.
	 */
	BRIEFCASE(actor -> actor.getInventory().add(CollectibleType.MONEY, CollectibleType.MONEY.getMaxStacks() / 100), 10);
	
	private Consumer<Actor> usageFunc;
	private int maxStacks;

	/**
	 * Class constructor.
	 * @param usageFunc
	 * 				The function of the item.
	 * @param maxStacks
	 * 				The max number of a specific type of items in the inventory.
	 */
	ItemsType(final Consumer<Actor> usageFunc, final int maxStacks) {
		this.usageFunc = usageFunc;
		this.maxStacks = maxStacks;
	}

	/**
	 * @return the action of the item when picked up.
	 */
	@Override
	public Consumer<Actor> usage() {
		return this.usageFunc;
	}

	/**
	 * @return the max number of a specific type of items in the inventory.
	 */
	@Override
	public int getMaxStacks() {
		return this.maxStacks;
	}
}
