package hotlinecesena.model.entities.components;

public interface LifeComponent extends Component {

	void takeDamage(double damage);
	
	double getCurrentHealth();
}
