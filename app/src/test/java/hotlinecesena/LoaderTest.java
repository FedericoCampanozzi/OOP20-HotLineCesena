package hotlinecesena;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import hotlinecesena.view.loader.AudioType;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyAudio;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;

class LoaderTest {

    @Test
    void imageLoaderTest() {
        try {
            new ProxyImage().getImage(SceneType.GAME, ImageType.RIFLE);
            new ProxyImage().getImage(SceneType.GAME, ImageType.SHOTGUN);
            new ProxyImage().getImage(SceneType.GAME, ImageType.BLANK);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void audioLoaderTest() {
        try {
            new ProxyAudio().getAudioClip(AudioType.DEATH);
            new ProxyAudio().getAudioClip(AudioType.WALK);
            new ProxyAudio().getAudioClip(AudioType.SHOOT);
            new ProxyAudio().getAudioClip(AudioType.RELOAD);
        } catch (Exception e) {
            fail();
        }
    }
}
