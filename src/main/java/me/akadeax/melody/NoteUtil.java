package me.akadeax.melody;

import org.bukkit.Note;

public class NoteUtil {
    public static Note getValue(int pitch) {
        int octave = pitch / 8;
        Note.Tone tone = Note.Tone.values()[pitch - (7 * octave)];
        return new Note(octave, tone, false);
    }
}
