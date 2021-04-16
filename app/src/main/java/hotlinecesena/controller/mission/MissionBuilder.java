package hotlinecesena.controller.mission;

import java.util.Random;

import hotlinecesena.controller.WorldController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.utilities.Utilities;
import static java.util.stream.Collectors.*;

public class MissionBuilder {

	private final MissionController mController;
	private Random rnd = new Random();
	private final WorldController wController;
	
	public MissionBuilder(WorldController wController) {
		mController= new MissionController();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		this.wController = wController;
	}
	
	public MissionBuilder addKillMission(int minEnemy, int maxEnemy) {
		maxEnemy = Math.min(JSONDataAccessLayer.getInstance().getEnemy().getEnemies().size(), maxEnemy);
		int enemy = Utilities.randomBetween(rnd, minEnemy, maxEnemy);
		mController.addQuest("Uccidi " +  enemy + " nemici",  
				() -> wController.getEnemyKilledByPlayer() == enemy);
		return this;
	}
	
	public MissionBuilder addAmmoMission(int minAmmoBox, int maxAmmoBox) {
		maxAmmoBox = Math.min(JSONDataAccessLayer.getInstance().getDataItems().getItems().entrySet().stream()
				.filter(itm -> itm.getValue().equals(ItemsType.AMMO_BAG))
				.collect(toSet()).size(), maxAmmoBox);
		
		int ammoBag = Utilities.randomBetween(rnd, minAmmoBox, maxAmmoBox);
		mController.addQuest("Raccogli " + ammoBag + " scatole di munizioni", 
				() -> wController.getTotalAmmoBag() >= ammoBag);
		return this;
	}
	
	public MissionBuilder addMedikitMission(int minMedikit, int maxMedikit) {
		maxMedikit = Math.min(JSONDataAccessLayer.getInstance().getDataItems().getItems().entrySet().stream()
				.filter(itm -> itm.getValue().equals(ItemsType.MEDIKIT))
				.collect(toSet()).size(), maxMedikit);
		
		int medikit = Utilities.randomBetween(rnd, minMedikit, maxMedikit);
		mController.addQuest("Raccogli " + medikit + " medikit",
				() -> wController.getTotalMedikit() >= medikit);
		return this;
	}
	
	public MissionBuilder addShootMission(int minAmmoShoot, int maxAmmoShoot) {
		int ammo = Utilities.randomBetween(rnd, minAmmoShoot, maxAmmoShoot);
		mController.addQuest("Spara " + ammo + " proiettili",  () -> wController.getTotalAmmoShootedByPlayer() >= ammo);
		return this;
	}
	
	public MissionBuilder addKeyObjectMission() {
		mController.addQuest("Cerca e Raccogli il denaro nascosto", () -> wController.isPickBriefCase());
		return this;
	}
	
	public MissionBuilder addChangeWeaponsMission(int minWeaponsChanged, int maxWeaponsChanged) {
		int weapons = Utilities.randomBetween(rnd, minWeaponsChanged, maxWeaponsChanged);
		mController.addQuest("Cambia " + weapons + " volte l'arma", () -> wController.getTotalWeaponsChanged() >= weapons);
		return this;
	}
	
	public MissionBuilder addSurviveMission(int minSeconds, int maxSeconds) {
		int seconds = Utilities.randomBetween(rnd, minSeconds, maxSeconds);
		mController.addQuest("Soppravivi per " + Utilities.convertSecondsToTimeString(seconds * 1000, "%dh %dm %ds"),  
				() -> wController.getPlayerLifeTime() >= seconds);
		return this;
	}
		
	public MissionController build() {
		return mController;
	}
}
