package hotlinecesena.model.entities.items;

import java.util.Optional;
import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum AmmunitionType implements Item {
	SHOTGUN_AMMO(20),
	RIFLE_AMMO(90),
	PISTOL_AMMO(30);
	
	private int maxStacks;

	AmmunitionType(int maxStacks) {
		this.maxStacks = maxStacks;
	}

	@Override
	public Optional<Consumer<Actor>> usage() {
		return Optional.empty();
	}

	@Override
	public int getMaxStacks() {
		return this.maxStacks;
	}

}
