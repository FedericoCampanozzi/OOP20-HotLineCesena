package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum ItemsType implements Item {
	MEDIKIT(actor -> actor.heal(50), 5),
	AMMO_BAG(actor -> {
		actor.getInventory().add(CollectibleType.SHOTGUN_AMMO, CollectibleType.SHOTGUN_AMMO.getMaxStacks() / 3);
		actor.getInventory().add(CollectibleType.RIFLE_AMMO, CollectibleType.RIFLE_AMMO.getMaxStacks() / 3);
		actor.getInventory().add(CollectibleType.PISTOL_AMMO, CollectibleType.PISTOL_AMMO.getMaxStacks() / 3);
		}, 5),
	BRIEFCASE(actor -> actor.getInventory().add(CollectibleType.MONEY, CollectibleType.MONEY.getMaxStacks() / 100), 10);
	
	private Consumer<Actor> usageFunc;
	private int maxStacks;

	ItemsType(final Consumer<Actor> usageFunc, final int maxStacks) {
		this.usageFunc = usageFunc;
		this.maxStacks = maxStacks;
	}

	@Override
	public Consumer<Actor> usage() {
		return this.usageFunc;
	}

	@Override
	public int getMaxStacks() {
		return this.maxStacks;
	}
}
