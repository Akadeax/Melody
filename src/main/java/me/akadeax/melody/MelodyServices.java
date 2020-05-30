package me.akadeax.melody;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MelodyServices implements Melody {
    private final MelodyPlugin plugin = MelodyPlugin.getPlugin(MelodyPlugin.class);

    int counter = 0;
    public void test() {
        if(counter == 0) {
            Bukkit.broadcastMessage("Test");
            ArrayList<CustomSound> customSounds = new ArrayList<>();

            customSounds.add(new CustomSound(MelodyInstrument.Piano, 0));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 1));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 2));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 3));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 4));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 400));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 4));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 400));

            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 4));

            customSounds.add(new CustomSound(MelodyInstrument.Pause, 800));

            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 5));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 4));

            customSounds.add(new CustomSound(MelodyInstrument.Pause, 800));

            customSounds.add(new CustomSound(MelodyInstrument.Piano, 3));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 3));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 3));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 3));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 2));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 400));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 2));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 400));

            customSounds.add(new CustomSound(MelodyInstrument.Piano, 1));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 1));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 1));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 1));
            customSounds.add(new CustomSound(MelodyInstrument.Pause, 200));
            customSounds.add(new CustomSound(MelodyInstrument.Piano, 0));

            CustomTrack track = new CustomTrack(customSounds);
            PlayTrack(track, Bukkit.getPlayer("Akadeax"));
            Bukkit.broadcastMessage("Played");
        }
        //counter++;
    }

    public void PlayTrack(MelodyTrack track, Player player) {
        Thread playThread = new Thread(() -> {
           for(MelodySound curr : track.getSounds()) {
               if(curr.isPause()) {
                   try {
                       Thread.sleep(curr.getPitch());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               else {
                   player.playNote(player.getLocation(), Instrument.PIANO, NoteUtil.getValue(curr.getPitch()));
               }
           }
        });
        playThread.start();
    }

    public CustomTrack createTrack(List<MelodySound> sounds) {
        return new CustomTrack(sounds);
    }
}
