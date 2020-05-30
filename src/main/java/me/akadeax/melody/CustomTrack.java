package me.akadeax.melody;

import java.util.ArrayList;
import java.util.List;

public class CustomTrack implements MelodyTrack {
    public List<MelodyNote> notes;

    public List<MelodyNote> getSounds() {
        return new ArrayList<>(notes);
    }

    public CustomTrack(List<MelodyNote> notes) {
        this.notes = notes;
    }
}