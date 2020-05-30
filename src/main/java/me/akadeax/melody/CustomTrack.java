package me.akadeax.melody;

import java.util.ArrayList;
import java.util.List;

public class CustomTrack implements MelodyTrack {
    public List<MelodySound> sounds;

    public List<MelodySound> getSounds() {
        return new ArrayList<>(sounds);
    }

    public CustomTrack(List<MelodySound> sounds) {
        this.sounds = sounds;
    }

}