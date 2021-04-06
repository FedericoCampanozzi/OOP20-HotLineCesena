package hotlinecesena.controller;

import java.util.Optional;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.events.AttackPerformedEvent;
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.loader.AudioType;

public class AudioEventController implements Subscriber{

    private AudioController audio;

    public AudioEventController() {
        JSONDataAccessLayer.getInstance().getEnemy().getEnemies().forEach(e -> {
            e.register(this);
        });
        JSONDataAccessLayer.getInstance().getPlayer().getPly().register(this);   
    }

    @Subscribe
    private void onMoveEvent(final MovementEvent e) {
        this.audio = new AudioController(Optional.of(e.getSource()));
        this.audio.playAudioClip(AudioType.WALK);
    }

    @Subscribe
    private void onRotationEvent(final RotationEvent e) {
        this.audio = new AudioController(Optional.of(e.getSource()));  
        this.audio.playAudioClip(AudioType.WALK);
    }

    @Subscribe
    private void onDeathEvent(final DeathEvent e) {
        this.audio = new AudioController(Optional.of(e.getSource()));
        this.audio.playAudioClip(AudioType.DEATH);
    }

    @Subscribe
    private void onAttackEvent(final AttackPerformedEvent e) {
        this.audio = new AudioController(Optional.of(e.getSource()));
        // TODO once weapons are created check which one is producing this event
        this.audio.playAudioClip(AudioType.SHOOT);
    }
}
