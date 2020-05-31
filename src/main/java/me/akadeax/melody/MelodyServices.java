package me.akadeax.melody;

import org.bukkit.Instrument;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class MelodyServices implements Melody {

    @Override
    public void playTrack(MelodyTrack track, Player player) {
        Thread playThread = new Thread(() -> {
            for(MelodyNote curr : track.getNotes()) {
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

    @Override
    public MelodyTrack createTrack(String name, List<MelodyNote> notes) {
        return new CustomTrack(name, notes);
    }

    @Override
    public MelodyNote createNote(MelodyInstrument instrument, MelodyTone tone, int octave) {
        return new CustomNote(instrument, tone, octave);
    }

    @Override
    public MelodyNote createPause(int time) {
        return new CustomNote(MelodyInstrument.Pause, MelodyTone.C, time);
    }

    @Override
    public String serializeTrack(MelodyTrack track) {
        return CustomTrack.serialize(track);
    }

    @Override
    public MelodyTrack deserializeTrack(String serializedTrack) {
        return CustomTrack.deserialize(serializedTrack);
    }

    @Override
    public void saveTrack(MelodyTrack track, String relPath) {
        throw new NotImplementedException();
    }

    @Override
    public MelodyTrack loadTrack(String relFilePath) {
        throw new NotImplementedException();
    }
}
