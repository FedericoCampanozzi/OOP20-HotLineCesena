package hotlinecesena.view.loader;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * ProxyImage is the surrogate of {@link ImageLoader}, the proxy is able
 * to control and secure access to the real audio loader (very similarly to {@link ProxyAudio})
 * and is able to add extra functionalities without changing the way an
 * audio file is loaded.
 */
public class ProxyImage implements ImageLoader {

    private static final String SEP = File.separator;

    private final ProxyImageLoader imageLoader;
    private final Map<ImageType, Image> loadedImages;

    public ProxyImage() {
        imageLoader = new ProxyImageLoader();
        loadedImages = new HashMap<>();
    }

    /**
     * Returns an {@code Image} object that can then be reproduced.
     * Once an image is loaded it's put in a {@code Map} to prevent the
     * same image to be loaded once more if it's already present
     * this helps prevent waste of resources
     * @see Map
     */
    @Override
    public Image getImage(final SceneType scene, final ImageType image) {
        if (loadedImages.containsKey(image)) {
            return loadedImages.get(image);
        } else {
            loadedImages.put(image, imageLoader.getImage(scene, image));
            return loadedImages.get(image);
        }
    }

    private static class ProxyImageLoader implements ImageLoader {
        private static final String PATH = "Images";
        private final ClassLoader classLoader = this.getClass().getClassLoader();

        @Override
        public Image getImage(final SceneType scene, final ImageType image) {
            final InputStream imageStream = classLoader.getResourceAsStream(
                    PATH + SEP + scene.toString() + SEP + image.toString());
            return new Image(imageStream);
        }
    }
}