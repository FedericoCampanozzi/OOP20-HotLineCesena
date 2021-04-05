package hotlinecesena.controller.generator;

import javafx.util.Pair;
import java.util.Map;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SIMBOLS_TYPE;

public class RectangolarRoom extends AbstractRoom {
	private  int w;
	private  int h;
	private  int d;
	
	private RectangolarRoom(Map<Pair<Integer, Integer>, SIMBOLS_TYPE> map, Pair<Integer, Integer> center, int width, int height) {
		super();
		this.map = map;
		this.w = width;
		this.h = height;
	}
	
	public RectangolarRoom(int width, int height, int nDoor) {
		super();
		
		if (width % 2 == 0) {
			width -= 1;
		}
		if (height % 2 == 0) {
			height -= 1;
		}
		
		this.h = height;
		this.w = width;
		this.d = nDoor;
		generate();
	}
	
	@Override
	public void generate() {
		
	}

	@Override
	public Room deepCopy() {
		return new RectangolarRoom(this.map, this.center, this.w, this.h);
	}
	
	public int getWidth() {
		return this.w;
	}
	
	public int getHeight() {
		return this.h;
	}
}
