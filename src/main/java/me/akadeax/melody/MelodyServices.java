package me.akadeax.melody;

import org.bukkit.Instrument;
import org.bukkit.entity.Player;

import java.util.List;

public class MelodyServices implements Melody {

    @Override
    public void playTrack(MelodyTrack track, Player player) {
        Thread playThread = new Thread(() -> {
            for(MelodyNote curr : track.getSounds()) {
                if(curr.isPause()) {
                    try {
                        Thread.sleep(curr.getOctave());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    player.playNote(player.getEyeLocation().add(0, 1.5d, 0), Instrument.PIANO, curr.getBukkitNote());
                }
            }
        });
        playThread.start();
    }

    public MelodyTrack createTrack(List<MelodyNote> notes) {
        return new CustomTrack(notes);
    }

    public MelodyNote createNote(MelodyInstrument instrument, MelodyTone tone, int octave) {
        return new CustomNote(instrument, tone, octave);
    }
    public MelodyNote createPause(int time) {
        return new CustomNote(MelodyInstrument.Pause, MelodyTone.C, time);
    }
}
