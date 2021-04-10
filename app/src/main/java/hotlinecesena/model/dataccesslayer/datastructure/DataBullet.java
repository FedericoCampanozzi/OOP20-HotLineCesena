package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.HashSet;
import java.util.Set;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.entities.items.Projectile;

public class DataBullet extends AbstractData {

	private final Set<Projectile> projectile = new HashSet<>();

	public Set<Projectile> getProjectile() {
		return projectile;
	}
}
