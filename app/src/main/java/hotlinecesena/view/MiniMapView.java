package hotlinecesena.view;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.dataccesslayer.SymbolsType;
import hotlinecesena.model.dataccesslayer.datastructure.DataJSONSettings;
import hotlinecesena.model.dataccesslayer.datastructure.DataWorldMap;
import hotlinecesena.utilities.Utilities;

public class MiniMapView {

	private WritableImage writtableMiniMap;
	private Pair<Integer, Integer> oldPlyPos;
	private DataJSONSettings settings = JSONDataAccessLayer.getInstance().getSettings();
	private DataWorldMap map = JSONDataAccessLayer.getInstance().getWorld();
	
	public MiniMapView() {
		
		int width =  settings.getPixelSize() * (map.getMaxX() - map.getMinX() + 1);
		int height = settings.getPixelSize() * (map.getMaxY() - map.getMinY() + 1);
		
		writtableMiniMap = new WritableImage(width, height);
		PixelWriter pw = writtableMiniMap.getPixelWriter();
		
		map.getWorldMap().entrySet().stream()
			.forEach(itm->{
				java.awt.Color color;
				if(	itm.getValue().equals(SymbolsType.VOID) || 
					itm.getValue().equals(SymbolsType.WALKABLE) ||
					itm.getValue().equals(SymbolsType.PLAYER) || 
					itm.getValue().equals(SymbolsType.WALL)){
					color = itm.getValue().getMiniMapColor();
				} else {
					color = SymbolsType.WALKABLE.getMiniMapColor();
				}
				
				pw.setColor(itm.getKey().getKey()- map.getMinX(), itm.getKey().getValue()- map.getMinY(), Utilities.convertColor(color));
		});
		
		oldPlyPos = Utilities.convertPoint2DToPair(JSONDataAccessLayer.getInstance().getPlayer().getPly().getPosition());
	}
	
	public ImageView getImageVIewUpdated() {
		PixelWriter pw = writtableMiniMap.getPixelWriter();
		pw.setColor(oldPlyPos.getKey()- map.getMinX(), oldPlyPos.getValue()- map.getMinY(), Utilities.convertColor(SymbolsType.WALKABLE.getMiniMapColor()));
		oldPlyPos = Utilities.convertPoint2DToPair(JSONDataAccessLayer.getInstance().getPlayer().getPly().getPosition());
		pw.setColor(oldPlyPos.getKey()- map.getMinX(), oldPlyPos.getValue()- map.getMinY(), Utilities.convertColor(SymbolsType.PLAYER.getMiniMapColor()));
		return new ImageView(writtableMiniMap);
	}
}
