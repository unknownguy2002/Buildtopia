package ninja.jrc.buildtopia.commands;

import ninja.jrc.buildtopia.Buildtopia;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BaseCommand implements CommandExecutor {
    private final Buildtopia plugin;


    public BaseCommand(Buildtopia plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1){
            sender.sendMessage("Buildtopia, basically a Growtopia rip-off");
            return true;
        }

        switch(args[0].toLowerCase()){

            case "": {

            }

        }

        return false;
    }
}
