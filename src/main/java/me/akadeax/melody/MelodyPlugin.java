package me.akadeax.melody;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class MelodyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        MelodyServices mel = new MelodyServices();
        getServer().getServicesManager().register(Melody.class, mel, this, ServicePriority.Normal);
    }
}
