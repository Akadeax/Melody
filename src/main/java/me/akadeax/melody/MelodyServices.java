package me.akadeax.melody;

import org.bukkit.Instrument;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

public class MelodyServices implements Melody {
    MelodyPlugin plugin;

    public MelodyServices(MelodyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void playTrack(MelodyTrack track, Player player) {
        final List<MelodyNote> notes = track.getNotes();

        new BukkitRunnable() {

            int currRow = 0;
            @Override
            public void run() {
                for(int i = 0; i < MelodyNote.bukkitToneAmount(); i++) {
                    MelodyNote curr = notes.get(i);
                    if(curr.getInstrument() != MelodyInstrument.PAUSE) {
                        Instrument bukkitInstrument = curr.getInstrument().toBukkitInstrument();
                        player.playNote(player.getEyeLocation(), bukkitInstrument, curr.getBukkitNote());
                    }
                }
            }
        }.runTaskTimer(plugin, 0, (60 / track.getBPM()) * 20);
    }

    @Override
    public MelodyTrack createTrack(String name, List<MelodyNote> notes, int BPM) {
        return new CustomTrack(name, notes, BPM);
    }

    @Override
    public MelodyNote createNote(MelodyInstrument instrument, MelodyTone tone, int octave) {
        return new CustomNote(instrument, tone, octave);
    }

    @Override
    public MelodyNote createPause(int pauseBeatAmount) {
        return new CustomNote(MelodyInstrument.PAUSE, MelodyTone.C, -1);
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
    public boolean saveTrack(MelodyTrack track, String folder) {
        File newMelFile = new File(String.format("%s/%s/%s.mel", plugin.getDataFolder(), folder, track.getName()));
        File parent = newMelFile.getParentFile();
        if(!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create folder: " + parent);
        }

        try (PrintWriter out = new PrintWriter(newMelFile)) {
            out.print(serializeTrack(track));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public MelodyTrack loadTrack(String relFilePath) {
        File toLoad = new File(String.format("%s/%s", plugin.getDataFolder(), relFilePath));
        if(!toLoad.exists()) {
            return null;
        }

        String trackText;
        try {
            trackText = new String(Files.readAllBytes(toLoad.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return deserializeTrack(trackText);
    }
}
