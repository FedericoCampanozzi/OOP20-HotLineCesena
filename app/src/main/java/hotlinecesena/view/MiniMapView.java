package hotlinecesena.view;

import java.util.function.Consumer;

import hotlinecesena.controller.Updatable;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MiniMapView implements Updatable{
	
	private static final int DIM = 300;
	private static final int CROP_SCALE = 5;
	
	private ImageView imageView = new ImageView();
	
	public MiniMapView(BorderPane primaryPane) {
		BorderPane borderPane = new BorderPane();
		borderPane.prefWidthProperty().bind(primaryPane.widthProperty());
		borderPane.setPrefHeight(DIM);
		Rectangle2D croppedPortion = new Rectangle2D(
				DIM / (CROP_SCALE * 10),
				DIM / (CROP_SCALE * 10),
				DIM / CROP_SCALE,
				DIM /CROP_SCALE);
		primaryPane.setTop(borderPane);
		borderPane.setRight(imageView);
		imageView.setFitHeight(DIM);
		imageView.setFitWidth(DIM);
		imageView.setViewport(croppedPortion);
	}

	@Override
	public Consumer<Double> getUpdateMethod() {
		return deltaTime -> {
			imageView.setImage(JSONDataAccessLayer.getInstance().getWorld().getImageVIewUpdated());
		};
	}
}
