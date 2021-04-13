package hotlinecesena.model.dataccesslayer;

import java.awt.Color;

public enum SymbolsType {
	VOID('_', Color.BLACK),
	DOOR('D', Color.CYAN),
	WALKABLE('.', Color.WHITE),
	ENEMY('E', Color.LIGHT_GRAY),
	PLAYER('P', Color.ORANGE),
	OBSTACOLES('O', Color.RED),
	WEAPONS('*', Color.BLUE),
	WALL('W', Color.GRAY),
	ITEM('I', Color.YELLOW),
	KEY_ITEM('K', Color.PINK);
	
	private final char c;
	private final Color color;
	
	private SymbolsType(char c, Color color) {
		this.c = c;
		this.color = color;
	}
	
	public char getDecotification() {
		return this.c;
	}
	
	public Color getColor() {
		return this.color;
	}
}