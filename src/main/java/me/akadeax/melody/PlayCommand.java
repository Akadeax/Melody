package me.akadeax.melody;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayCommand implements CommandExecutor {

    private Melody melody;
    public PlayCommand(Melody melody) {
        this.melody = melody;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player p = (Player)sender;
        if(args.length != 1) return false;
        MelodyTrack track = melody.loadTrack(args[0]);
        melody.playTrack(track, p);

        return true;
    }

}
