package me.akadeax.melody;

import java.util.ArrayList;
import java.util.List;

public class CustomTrack implements MelodyTrack {
    public List<List<MelodyNote>> noteCols;
    public String name;
    public int BPM;

    public List<List<MelodyNote>> getNoteCols() {
        return new ArrayList<>(noteCols);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBPM() {
        return BPM;
    }

    public CustomTrack(String name, List<List<MelodyNote>> noteCols, int BPM) {
        this.name = name;
        this.noteCols = noteCols;
        this.BPM = BPM;
    }

    private CustomTrack() {
        this.name = "";
        this.noteCols = new ArrayList<>();
        this.BPM = 60;
    }

    public static String serialize(MelodyTrack track) {
        StringBuilder builder = new StringBuilder();

        // add metadata
        builder.append(track.getName()).append("/");
        builder.append(track.getBPM()).append("/");

        // add all notes
        for(List<MelodyNote> currNoteCol : track.getNoteCols()) {
            for(MelodyNote currNote : currNoteCol) {
                builder.append(CustomNote.serialize(currNote)).append(";");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("/");
        }
        // trim last "/"
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }


    public static final int METADATA_AMT = 2;
    public static MelodyTrack deserialize(String serializedTrack) {
        CustomTrack newTrack = new CustomTrack();

        // with metadata
        String[] totalSplit = serializedTrack.split("/");
        // without metadata (only all the notes)
        String[] notesSplit = new String[totalSplit.length - METADATA_AMT];
        System.arraycopy(totalSplit, METADATA_AMT, notesSplit, 0, notesSplit.length);


        // load metadata and all note columns
        newTrack.name = totalSplit[0];
        newTrack.BPM = Integer.parseInt(totalSplit[1]);
        for(String currColString : notesSplit) {
            List<MelodyNote> currColNotes = new ArrayList<>();

            for(String currNoteString : currColString.split(";")) {
                currColNotes.add(CustomNote.deserialize(currNoteString));
            }
            newTrack.noteCols.add(currColNotes);
        }

        return newTrack;
    }
}