package hotlinecesena.controller.mission;

/**
 * An interface that define a builder for {@code MissionController}
 * @author Federico
 *
 */
public interface MissionBuilder {
	
	/**
	 * Add a mission that for be completed the player needs to kill some enemy
	 * @param minEnemy
	 * @param maxEnemy
	 * @return
	 */
	MissionBuilder addKillMission(int minEnemy, int maxEnemy);

	/**
	 * Add a mission that for be completed the player needs to pick some ammo bag
	 * @param minAmmoBox
	 * @param maxAmmoBox
	 * @return
	 */
	MissionBuilder addAmmoMission(int minAmmoBox, int maxAmmoBox);

	/**
	 * Add a mission that for be completed the player needs to pick some medikit
	 * @param minMedikit
	 * @param maxMedikit
	 * @return
	 */
	MissionBuilder addMedikitMission(int minMedikit, int maxMedikit);

	/**
	 * Add a mission that for be completed the player needs to shoot some bullet with the weapon he wants
	 * @param minAmmoShoot
	 * @param maxAmmoShoot
	 * @return
	 */
	MissionBuilder addShootMission(int minAmmoShoot, int maxAmmoShoot);

	/**
	 * Add a mission that for be completed the player needs to search and pick he key items
	 * @return
	 */
	MissionBuilder addKeyObjectMission();

	/**
	 * Add a mission that for be completed the player needs to pick and change different weapons
	 * @param minWeaponsChanged
	 * @param maxWeaponsChanged
	 * @return
	 */
	MissionBuilder addChangeWeaponsMission(int minWeaponsChanged, int maxWeaponsChanged);

	/**
	 * Add a mission that for be completed the player needs to survive for a period of time
	 * @param minSeconds
	 * @param maxSeconds
	 * @return
	 */
	MissionBuilder addSurviveMission(int minSeconds, int maxSeconds);

	/**
	 * The final {@code MissionController} created
	 * @return
	 */
	MissionController build();

}