package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum CollectibleType implements Item {
	MONEY(1000),
	SHOTGUN_AMMO(21),
	RIFLE_AMMO(90),
	PISTOL_AMMO(30);
	
	private int maxStacks;

	CollectibleType(int maxStacks) {
		this.maxStacks = maxStacks;
	}

	@Override
	public Consumer<Actor> usage() {
		return null;
	}

	@Override
	public int getMaxStacks() {
		return this.maxStacks;
	}

}
