package me.akadeax.melody;

import java.util.ArrayList;
import java.util.List;

public class CustomTrack implements MelodyTrack {
    public List<MelodyNote> notes;
    public String name;
    public int BPM;

    public List<MelodyNote> getNotes() {
        return new ArrayList<>(notes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBPM() {
        return BPM;
    }

    public CustomTrack(String name, List<MelodyNote> notes, int BPM) {
        this.name = name;
        this.notes = notes;
        this.BPM = BPM;
    }

    private CustomTrack() {
        this.name = "";
        this.notes = new ArrayList<>();
        this.BPM = 60;
    }

    // in format "NAME;BPM;INSTRUMENT,OCTAVE,TONE;INSTRUMENT,OCTAVE,TONE;INSTRUMENT,OCTAVE,TONE...
    public static String serialize(MelodyTrack track) {
        StringBuilder builder = new StringBuilder();

        // add metadata
        builder.append(track.getName()).append(";");
        builder.append(track.getBPM()).append(";");

        // add all notes
        for(MelodyNote note : track.getNotes()) {
            builder.append(CustomNote.serialize(note)).append(";");
        }
        // trim last ';'
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }


    public static final int METADATA_AMT = 2;
    public static MelodyTrack deserialize(String serializedTrack) {
        CustomTrack newTrack = new CustomTrack();

        // with metadata
        String[] totalSplit = serializedTrack.split(";");
        // without metadata
        String[] notesSplit = new String[totalSplit.length - METADATA_AMT];
        System.arraycopy(totalSplit, METADATA_AMT, notesSplit, 0, notesSplit.length);

        // load metadata and all notes
        newTrack.name = totalSplit[0];
        newTrack.BPM = Integer.parseInt(totalSplit[1]);

        for(String currNote : notesSplit) {
            newTrack.notes.add(CustomNote.deserialize(currNote));
        }

        return newTrack;
    }
}