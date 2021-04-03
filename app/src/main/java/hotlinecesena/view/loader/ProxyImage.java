package hotlinecesena.view.loader;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ProxyImage implements ImageLoader {

    private final ProxyImageLoader imageLoader;
    private final Map<ImageType, Image> loadedImages;
    
    public ProxyImage() {
        this.imageLoader = new ProxyImageLoader();
        this.loadedImages = new HashMap<>();
    }
    
    @Override
    public Image getImage(SceneType scene, ImageType image) {
        if(this.loadedImages.containsKey(image)) {
            return loadedImages.get(image);
        } else {
            this.loadedImages.put(image, this.imageLoader.getImage(scene, image));
            return this.loadedImages.get(image);
        }
    }

    private static class ProxyImageLoader implements ImageLoader {
        private final static String PATH = "src" + File.separator + "main" + File.separator + "resources";

        @Override
        public Image getImage(SceneType scene, ImageType image) {
            return new Image(Paths.get(PATH + File.separator + scene.toString() + File.separator + image.toString()).toUri().toString());
        }

    }
    
}
