package hotlinecesena.view.loader;

import javafx.scene.image.Image;

/**
 * ImageLoader is the actual class that perform image loading,
 * once loaded the requested image can be shown, moved or rotated
 * on the screen
 */
public interface ImageLoader {

    /**
     * Returns an {@code Image} object that can then be painted on the screen.
     * The image is loaded by adding an absolute path to the two
     * arguments
     * @param scene the relative path to the type of file needed
     * @param image the relative path of the file that needs to be loaded
     * @return an {@code Image} that can be displayed
     * @see Image
     * @see SceneType
     * @see ImageType
     */
    public Image getImage(SceneType scene, ImageType image);

}
