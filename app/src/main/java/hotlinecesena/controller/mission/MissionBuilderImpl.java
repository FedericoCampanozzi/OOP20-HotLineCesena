package hotlinecesena.controller.mission;

import java.util.Random;

import hotlinecesena.controller.WorldController;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.entities.items.ItemsType;
import hotlinecesena.utilities.Utilities;

import static java.util.stream.Collectors.*;

public class MissionBuilderImpl implements MissionBuilder {

	private final MissionController mController;
	private Random rnd = new Random();
	private final WorldController wController;

	public MissionBuilderImpl(WorldController wController) {
		mController = new MissionControllerImpl();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		this.wController = wController;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addKillMission(int minEnemy, int maxEnemy) {
		int possible = JSONDataAccessLayer.getInstance().getEnemy().getEnemies().size();
		maxEnemy = Math.min(maxEnemy, possible);
		minEnemy = Math.min(minEnemy, possible);

		if (minEnemy < maxEnemy && minEnemy != 0) {
			int enemy = Utilities.randomBetween(rnd, minEnemy, maxEnemy);
			mController.addQuest("Uccidi " + enemy + " nemici", () -> wController.getEnemyKilledByPlayer() == enemy);
		}

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addAmmoMission(int minAmmoBox, int maxAmmoBox) {
		int possible = JSONDataAccessLayer.getInstance().getDataItems().getItems().entrySet().stream()
				.filter(itm -> itm.getValue().equals(ItemsType.AMMO_BAG)).collect(toSet()).size();
		maxAmmoBox = Math.min(maxAmmoBox, possible);
		minAmmoBox = Math.min(minAmmoBox, possible);

		if (minAmmoBox < maxAmmoBox && minAmmoBox != 0) {
			int ammoBag = Utilities.randomBetween(rnd, minAmmoBox, maxAmmoBox);
			mController.addQuest("Raccogli " + ammoBag + " scatole di munizioni",
					() -> wController.getTotalAmmoBag() >= ammoBag);
		}

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addMedikitMission(int minMedikit, int maxMedikit) {
		int possible = JSONDataAccessLayer.getInstance().getDataItems().getItems().entrySet().stream()
				.filter(itm -> itm.getValue().equals(ItemsType.MEDIKIT)).collect(toSet()).size();
		minMedikit = Math.min(minMedikit, possible);
		maxMedikit = Math.min(maxMedikit, possible);

		if (minMedikit < maxMedikit && minMedikit != 0) {
			int medikit = Utilities.randomBetween(rnd, minMedikit, maxMedikit);
			mController.addQuest("Raccogli " + medikit + " medikit", () -> wController.getTotalMedikit() >= medikit);
		}

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addShootMission(int minAmmoShoot, int maxAmmoShoot) {
		int ammo = Utilities.randomBetween(rnd, minAmmoShoot, maxAmmoShoot);
		mController.addQuest("Spara " + ammo + " proiettili", () -> wController.getTotalAmmoShootedByPlayer() >= ammo);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addKeyObjectMission() {
		mController.addQuest("Cerca e Raccogli il denaro nascosto", () -> wController.isPickBriefCase());
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addChangeWeaponsMission(int minWeaponsChanged, int maxWeaponsChanged) {
		int possible = JSONDataAccessLayer.getInstance().getWorld().getWorldMap().entrySet().stream()
				.filter(itm -> itm.getValue().equals(SymbolsType.WEAPONS)).collect(toSet()).size();
		minWeaponsChanged = Math.min(minWeaponsChanged, possible);
		maxWeaponsChanged = Math.min(maxWeaponsChanged, possible);

		if (minWeaponsChanged < maxWeaponsChanged && minWeaponsChanged != 0) {
			int weapons = Utilities.randomBetween(rnd, minWeaponsChanged, maxWeaponsChanged);
			mController.addQuest("Cambia " + weapons + " volte l'arma",
					() -> wController.getTotalWeaponsChanged() >= weapons);
		}

		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionBuilder addSurviveMission(int minSeconds, int maxSeconds) {
		int seconds = Utilities.randomBetween(rnd, minSeconds, maxSeconds);
		mController.addQuest("Soppravivi per " + Utilities.convertSecondsToTimeString(seconds * 1000, "%dh %dm %ds"),
				() -> wController.getPlayerLifeTime() >= seconds);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MissionController build() {
		return mController;
	}
}
