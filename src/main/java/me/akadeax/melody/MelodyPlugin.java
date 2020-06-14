package me.akadeax.melody;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class MelodyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        MelodyServices service = new MelodyServices(this);
        getServer().getServicesManager().register(Melody.class, service, this, ServicePriority.Normal);


        PluginCommand playCmd = getCommand("play");
        if(playCmd == null) {
            System.out.println("Couldn't find commands, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        playCmd.setExecutor(new PlayCommand(service));
    }
}
