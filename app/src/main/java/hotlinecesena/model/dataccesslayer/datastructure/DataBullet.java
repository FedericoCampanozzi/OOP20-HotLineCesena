package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.entities.items.Projectile;

/***
 * Class that provide to store a list of bullet {@linkProjectile}
 * @author Federico
 *
 */
public class DataBullet extends AbstractData {
	
    private final List<Projectile> projectile = new CopyOnWriteArrayList<>();
    
    public List<Projectile> getProjectile() {
        return projectile;
    }
}