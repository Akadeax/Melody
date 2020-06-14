package me.akadeax.melody;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class MelodyServices implements Melody {
    MelodyPlugin plugin;

    public MelodyServices(MelodyPlugin plugin) {
        this.plugin = plugin;
    }

    private HashMap<Integer, PlayTrackTask> tasks = new HashMap<>();

    @Override
    public int playTrack(MelodyTrack track, Player player) {
        callEvent(new TrackStartEvent(track, player));

        track.getBPM();
        // convert from BPM to time to wait (in ticks) between beats
        final long period = PlayTrackTask.bpmToTicks(track.getBPM());

        PlayTrackTask task = new PlayTrackTask(this, track, player);
        BukkitTask startedTask = task.runTaskTimer(plugin, 0, period);

        tasks.put(startedTask.getTaskId(), task);
        return startedTask.getTaskId();
    }

    @Override
    public boolean stopTrack(int taskID) {
        if(tasks.containsKey(taskID)) {
            plugin.getServer().getScheduler().cancelTask(taskID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean fadeoutTrack(int taskID, int fadeoutTime) {
        if(tasks.containsKey(taskID)) {
            tasks.get(taskID).fadeoutTrack(fadeoutTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isTrackPlaying(int taskID) {
        return tasks.containsKey(taskID);
    }

    @Override
    public MelodyTrack createTrack(String name, List<List<MelodyNote>> noteCols, int BPM) {
        return new CustomTrack(name, noteCols, BPM);
    }

    @Override
    public MelodyNote createNote(MelodyInstrument instrument, MelodyTone tone, int octave) {
        return new CustomNote(instrument, tone, octave);
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
        File newMelFile = new File(String.format("%s/%s/%s.melody", plugin.getDataFolder(), folder, track.getName()));
        File parent = newMelFile.getParentFile();
        if(!parent.exists() && !parent.mkdirs()) {
            System.out.println("Couldn't create folder: " + parent);
            return false;
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

    @Override
    public void callEvent(Event e) {
        plugin.getServer().getPluginManager().callEvent(e);
    }
}
