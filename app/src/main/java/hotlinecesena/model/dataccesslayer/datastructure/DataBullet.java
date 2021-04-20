package hotlinecesena.model.dataccesslayer.datastructure;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.entities.items.Projectile;

/***
 * Class that provide to memorizate a bullet data
 * @author Federico
 *
 */
public class DataBullet extends AbstractData {

    private final List<Projectile> projectile = new CopyOnWriteArrayList<>();

    /**
     * get the list of bullet
     * @return the list of bullet {@linkProjectile}
     */
    public List<Projectile> getProjectile() {
        return projectile;
    }
}