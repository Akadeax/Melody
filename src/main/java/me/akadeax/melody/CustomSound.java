package me.akadeax.melody;

import org.bukkit.Note;

public class CustomSound implements MelodySound {
    public MelodyInstrument instrument;
    public int pitch;

    public MelodyInstrument getInstrument() {
        return instrument;
    }

    public int getPitch() {
        return pitch;
    }

    public boolean isPause() {
        return instrument.equals(MelodyInstrument.Pause);
    }

    public CustomSound(MelodyInstrument instrument, int pitch) {
        this.instrument = instrument;
        this.pitch = pitch;
    }
}
