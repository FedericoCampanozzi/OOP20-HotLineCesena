package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.ArrayList;
import java.util.List;

import hotlinecesena.model.dataccesslayer.AbstractData;

public class DataBullet extends AbstractData {

	private final List<Integer> projectile = new ArrayList<>();

	public List<Integer> getProjectile() {
		return projectile;
	}
}
