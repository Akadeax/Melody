package me.akadeax.melody;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class PlayTrackTask extends BukkitRunnable {

    final Melody services;

    final MelodyTrack toPlay;
    final Player playFor;

    int currCol = 0;

    public PlayTrackTask(Melody services, MelodyTrack toPlay, Player toPlayFor) {
        this.services = services;
        this.toPlay = toPlay;
        this.playFor = toPlayFor;

        noteCols = toPlay.getNoteCols();
        period = bpmToTicks(toPlay.getBPM());
    }


    final int toneAmt = MelodyNote.bukkitToneAmount();
    final List<List<MelodyNote>> noteCols;
    final int period;

    @Override
    public void run() {
        Vector playerFacing = playFor.getLocation().getDirection();
        Location playLoc = playFor.getEyeLocation().add(playerFacing);

        if(fadeout) {
            /*
            Alright: we first determine how often this track would tick within fadeoutTime -> fadeoutTime / period
            we then determine how far we should move per tick that we're fading out -> 10 / fadeoutBeatsAmt
            we then add (that * amount of beats since fadeout start) to the location our track plays at to
            get a distancing effect.
            ie. first beat while we're fading out playLoc is x blocks away, next beat it's 2x blocks away, etc.
            hope this makes it a bit clearer to anyone adventurous enough to scavenge through this code.
            (48 is the amount of blocks a noteblock can be heard from, according to the wiki, at least.)
             */
            final int fadeoutBeatsAmt = fadeoutTime / period;
            Vector segmentPerFadeoutBeat = playerFacing.multiply(48 / fadeoutBeatsAmt);
            playLoc.add(segmentPerFadeoutBeat.multiply(currentFadeoutAmount));

            currentFadeoutAmount++;
            if(currentFadeoutAmount * period > fadeoutTime) {
                services.callEvent(new TrackEndEvent(toPlay, playFor));
                this.cancel();
                return;
            }
        }

        for(MelodyNote curr : noteCols.get(currCol)) {
            Instrument bukkitInstrument = curr.getInstrument().toBukkitInstrument();
            playFor.playNote(playLoc, bukkitInstrument, curr.getBukkitNote());
        }

        if(currCol >= noteCols.size() - 1) {
            services.callEvent(new TrackEndEvent(toPlay, playFor));
            this.cancel();
        } else {
            services.callEvent(new TrackTickEvent(toPlay, playFor));
            currCol++;
        }
    }

    public Player getPlayer() {
        return playFor;
    }

    public MelodyTrack getTrack() {
        return toPlay;
    }

    private boolean fadeout = false;
    private int fadeoutTime = 0;
    private int currentFadeoutAmount = 0;
    public void fadeoutTrack(int time) {
        Bukkit.broadcastMessage("fading out...");
        fadeout = true;
        fadeoutTime = time;
    }

    public static int bpmToTicks(int bpm) {
        return (int)(60 / (double)bpm * 20);
    }
}
