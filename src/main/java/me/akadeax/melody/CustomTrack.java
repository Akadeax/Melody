package me.akadeax.melody;

import java.util.ArrayList;
import java.util.List;

public class CustomTrack implements MelodyTrack {
    public List<MelodyNote> notes;
    public String name;

    public List<MelodyNote> getNotes() {
        return new ArrayList<>(notes);
    }

    @Override
    public String getName() {
        return null;
    }

    public CustomTrack(String name, List<MelodyNote> notes) {
        this.name = name;
        this.notes = notes;
    }

    private CustomTrack() {
        this.name = "";
        this.notes = new ArrayList<>();
    }

    /**
     * in format "NAME;INSTRUMENT,OCTAVE,TONE;INSTRUMENT,OCTAVE,TONE;INSTRUMENT,OCTAVE,TONE...
     */
    public static String serialize(MelodyTrack track) {
        StringBuilder builder = new StringBuilder();

        builder.append(track.getName()).append(";");

        for(MelodyNote note : track.getNotes()) {
            builder.append(CustomNote.serialize(note)).append(";");
        }

        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static MelodyTrack deserialize(String serializedTrack) {
        CustomTrack newTrack = new CustomTrack();

        String[] split = serializedTrack.split(";");
        for(int i = 0; i < split.length; i++) {
            if(i == 0) {
                newTrack.name = split[i];
            } else {
                newTrack.notes.add(CustomNote.deserialize(split[i]));
            }
        }

        return newTrack;
    }
}