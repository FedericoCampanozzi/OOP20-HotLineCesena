package hotlinecesena.controller.mission;

public interface MissionBuilder {
	
	/**
	 * Add a mission that for be completed the player needs to kill some enemy
	 * @param minEnemy
	 * @param maxEnemy
	 * @return
	 */
	MissionBuilder addKillMission(int minEnemy, int maxEnemy);

	/**
	 * 
	 * @param minAmmoBox
	 * @param maxAmmoBox
	 * @return
	 */
	MissionBuilder addAmmoMission(int minAmmoBox, int maxAmmoBox);

	/**
	 * 
	 * @param minMedikit
	 * @param maxMedikit
	 * @return
	 */
	MissionBuilder addMedikitMission(int minMedikit, int maxMedikit);

	/**
	 * 
	 * @param minAmmoShoot
	 * @param maxAmmoShoot
	 * @return
	 */
	MissionBuilder addShootMission(int minAmmoShoot, int maxAmmoShoot);

	/**
	 * 
	 * @return
	 */
	MissionBuilder addKeyObjectMission();

	/**
	 * 
	 * @param minWeaponsChanged
	 * @param maxWeaponsChanged
	 * @return
	 */
	MissionBuilder addChangeWeaponsMission(int minWeaponsChanged, int maxWeaponsChanged);

	/**
	 * 
	 * @param minSeconds
	 * @param maxSeconds
	 * @return
	 */
	MissionBuilder addSurviveMission(int minSeconds, int maxSeconds);

	/**
	 * 
	 * @return
	 */
	MissionController build();

}