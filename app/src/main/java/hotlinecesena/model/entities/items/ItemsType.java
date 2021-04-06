package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum ItemsType implements Item {
	MEDIKIT(actor -> actor.heal(50), 5),
	// 3 add, una per ogni tipo di munizone
	AMMO_BAG(actor -> {
		actor.getInventory().add(AmmunitionType.SHOTGUN_AMMO, AmmunitionType.SHOTGUN_AMMO.getMaxStacks() / 3);
		actor.getInventory().add(AmmunitionType.RIFLE_AMMO, AmmunitionType.RIFLE_AMMO.getMaxStacks() / 3);
		actor.getInventory().add(AmmunitionType.PISTOL_AMMO, AmmunitionType.PISTOL_AMMO.getMaxStacks() / 3);
		}, 5);
	
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
