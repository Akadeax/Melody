package me.akadeax.melody;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
        final int toneAmt = MelodyNote.bukkitToneAmount();

        final long period = (long)((double)60 / track.getBPM() * 20);
        new BukkitRunnable() {

            int currRow = 0;
            @Override
            public void run() {
                int start = currRow * toneAmt;
                // end (pause) after either start + 25 or after the last note was reached (notes.length - start)
                int end = start + Math.min(notes.size() - start, toneAmt);

                Vector playerFacing = player.getLocation().getDirection().multiply(5);
                Location playLoc = player.getEyeLocation().add(playerFacing);

                for(int i = start; i < end; i++) {
                    MelodyNote curr = notes.get(i);

                    if(curr.getInstrument() != MelodyInstrument.PAUSE) {
                        Instrument bukkitInstrument = curr.getInstrument().toBukkitInstrument();
                        player.playNote(playLoc, bukkitInstrument, curr.getBukkitNote());
                    }
                }
                currRow++;

                if(currRow * toneAmt >= notes.size()) this.cancel();
            }
        }.runTaskTimer(plugin, 0, period);
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
    public MelodyNote createPause() {
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
