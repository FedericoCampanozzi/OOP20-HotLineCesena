package hotlinecesena.model.dataccesslayer;

import java.awt.Color;

public enum SymbolsType {
	
	VOID		('_', new Color(  0,   0,   0), new Color(  0, 153,   0)),
	DOOR		('D', new Color(204,  51,   0), new Color(  0,   0,   0)),
	WALKABLE	('.', new Color(255, 255, 255), new Color(255, 166,  77)),
	ENEMY		('E', new Color(243,   0,   0), new Color(  0,   0,   0)),
	PLAYER		('P', new Color(153,   0,  77), new Color( 82,   0, 204)),
	OBSTACOLES	('O', new Color( 92,  92, 061), new Color(  0,   0,   0)),
	WEAPONS		('*', new Color(179, 179,   0), new Color(  0,   0,   0)),
	WALL		('W', new Color(128,  64,   0), new Color(102,  68,   0)),
	ITEM		('I', new Color(102,   0, 102), new Color(  0,   0,   0)),
	KEY_ITEM	('K', new Color(153,  51, 153), new Color(  0,   0,   0)),
	REMOVE		('-', new Color(153, 255, 204), new Color(  0,   0,   0));
	
	private final char c;
	private final Color testColor;
	private final Color miniMapColor;
	
	private SymbolsType(char c, Color t, Color m) {
		this.c = c;
		this.testColor = t;
		this.miniMapColor = m;
	}
	
	public char getDecotification() {
		return this.c;
	}
	
	public Color getTestColor() {
		return this.testColor;
	}
	
	public Color getMiniMapColor() {
		return this.miniMapColor;
	}
}