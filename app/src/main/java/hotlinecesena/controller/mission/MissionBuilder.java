package hotlinecesena.controller.mission;

public interface MissionBuilder {
	
	MissionBuilder addKillMission(int minEnemy, int maxEnemy);

	MissionBuilder addAmmoMission(int minAmmoBox, int maxAmmoBox);

	MissionBuilder addMedikitMission(int minMedikit, int maxMedikit);

	MissionBuilder addShootMission(int minAmmoShoot, int maxAmmoShoot);

	MissionBuilder addKeyObjectMission();

	MissionBuilder addChangeWeaponsMission(int minWeaponsChanged, int maxWeaponsChanged);

	MissionBuilder addSurviveMission(int minSeconds, int maxSeconds);

	MissionController build();

}