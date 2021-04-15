package hotlinecesena.controller;

import java.util.Random;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.Utilities;

public class MissionFactory {

	public static MissionController defaultMissions(int minEnemyToKill, 
			int minAmmoShoot, int maxAmmoShoot,
			int minSecTimeSurvive, int maxSecTimeSurvive) {
		final MissionController mController = new MissionController();
		final Random rnd = new Random();
		rnd.setSeed(JSONDataAccessLayer.SEED);
		final int enemyKill = Utilities.randomBetween(rnd, minEnemyToKill, JSONDataAccessLayer.getInstance().getEnemy().getEnemies().size());
		final int ammoShoot = Utilities.randomBetween(rnd, minAmmoShoot, maxAmmoShoot);
		final int secSurvive = Utilities.randomBetween(rnd, minSecTimeSurvive, maxSecTimeSurvive);
		
		mController.addQuest("Cerca e Raccogli il denaro nascosto", () -> true);
		mController.addQuest("Uccidi " + enemyKill + " nemici",  () -> false);
		mController.addQuest("Spara " + ammoShoot + " proiettili",  () -> true);
		mController.addQuest("Soppravivi per " + Utilities.convertSecondsToTimeString(secSurvive, "%dh %dm %ds"),  () -> true);
		
		return mController;
	}
}
