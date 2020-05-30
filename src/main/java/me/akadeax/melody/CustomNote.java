package me.akadeax.melody;

import org.bukkit.Note;

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
}
