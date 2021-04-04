package hotlinecesena.model.entities.items;

import java.util.Optional;
import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public class WeaponImpl implements Weapon{

	@Override
	public Optional<Consumer<Actor>> usage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxStacks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reload(int bullets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item getCompatibleAmmunition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getReloadTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getNoise() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMagazineSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentAmmo() {
		// TODO Auto-generated method stub
		return 0;
	}

}
