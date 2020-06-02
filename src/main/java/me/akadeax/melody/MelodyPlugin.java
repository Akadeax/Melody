package me.akadeax.melody;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class MelodyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        MelodyServices srvc = new MelodyServices(this);
        getServer().getServicesManager().register(Melody.class, srvc, this, ServicePriority.Normal);
    }
}
