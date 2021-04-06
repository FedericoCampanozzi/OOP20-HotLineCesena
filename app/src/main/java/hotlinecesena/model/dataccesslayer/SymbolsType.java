package hotlinecesena.model.dataccesslayer;

public enum SymbolsType {
	VOID('_'),
	DOOR('D'),
	WALKABLE('.'),
	ENEMY('E'),
	PLAYER('P'),
	MEDIKIT('M'),
	AMMO('A'),
	OBSTACOLES('O'),
	WALL('W');
	
	private final char c;
	
	private SymbolsType(char c) {
		this.c = c;
	}
	
	public char getDecotification() {
		return this.c;
	}
}