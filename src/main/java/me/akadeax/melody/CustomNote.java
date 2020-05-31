package me.akadeax.melody;

import org.bukkit.Note;

import java.security.InvalidParameterException;
import java.text.ParseException;

public class CustomNote implements MelodyNote {

    public MelodyInstrument instrument;
    public MelodyTone tone;
    public int octave;

    @Override
    public MelodyInstrument getInstrument() {
        return instrument;
    }

    @Override
    public MelodyTone getTone() {
        return tone;
    }

    @Override
    public Note getBukkitNote() {
        return tone.getBukkitNote(octave);
    }

    @Override
    public int getOctave() {
        return octave;
    }

    @Override
    public boolean isPause() {
        return instrument.equals(MelodyInstrument.Pause);
    }

    public CustomNote(MelodyInstrument instrument, MelodyTone tone, int octave) {
        this.instrument = instrument;
        this.tone = tone;
        this.octave = octave;
    }

    /**
     * Checks whether Note to play is between F#0 and F#2
     * (cuz that's minecrafts noteblock range??)
     */
    @Override
    public boolean isPlayableInGame() {
        MelodyTone[] tones = MelodyTone.values();
        int thisNoteIndex = -1;
        for(int i = 0; i < tones.length; i++) {
            if(tones[i].equals(tone)) {
                thisNoteIndex = i;
                break;
            }
        }

             /* why check against 5? the fifth Tone is F, so if this note
                is below F# (6) on octave 0, it's not playable; if this note
                is above F# (6) on octave 2, it's not playable */
        return !((thisNoteIndex < 6 && octave <= 0) || (thisNoteIndex > 6 && octave >= 2));
    }


    public static String serialize(MelodyNote note) {
        return String.format("%s,%s,%s", note.getInstrument().index, note.getOctave(), note.getTone().toString());
    }

    public static MelodyNote deserialize(String serializedNote) {
        try {
            String[] split = serializedNote.split(",");
            if(split.length != 3) throw new ParseException(serializedNote, 0);

            return new CustomNote(
                    MelodyInstrument.getByIndex(Integer.parseInt(split[0])),
                    MelodyTone.valueOf(split[1]),
                    Integer.parseInt(split[2])
            );
        } catch(ParseException e) {
            throw new InvalidParameterException(String.format("Failed to deserialize note from '%s'!", serializedNote));
        }
    }
}
