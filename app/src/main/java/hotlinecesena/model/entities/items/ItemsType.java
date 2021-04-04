package hotlinecesena.model.entities.items;

import java.util.Optional;
import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.player.Player;

public enum ItemsType implements Item {
	MEDIKIT(actor -> actor.heal(50), 5),
	AMMO_BAG(actor -> actor.addAmmo(14, 60, 20), 5);
	
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
