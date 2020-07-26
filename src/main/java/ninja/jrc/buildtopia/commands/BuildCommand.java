package ninja.jrc.buildtopia.commands;

import ninja.jrc.buildtopia.Buildtopia;
import ninja.jrc.buildtopia.Lang;
import ninja.jrc.buildtopia.managers.WorldWrapperManager;
import ninja.jrc.buildtopia.objects.WorldWrapper;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Optional;

public class BuildCommand implements CommandExecutor {
    private final Buildtopia plugin;
    private final Server server;
    private final WorldWrapperManager worldWrapperManager;

    public BuildCommand(Buildtopia plugin, WorldWrapperManager worldWrapperManager){
        this.plugin = plugin;
        this.server = plugin.getServer();

        this.worldWrapperManager = worldWrapperManager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.PLAYER_ONLY.getPhraseWithPrefix()));
            return false;
        }
        Player player = (Player) sender;

        if(args.length < 1){
            return false;
        }

        switch(args[0].toLowerCase()){

            // New world/build
            case "new": {
                if(args.length < 2){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.INSUFFICIENT_ARGUMENTS.getPhraseWithPrefix()));
                    return false;
                }

                if(args[1].length() > 15){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.NAME_TOO_LONG.getPhraseWithPrefix()));
                    return false;
                }

                if(!args[1].matches("^[A-Za-z0-9_-]*$")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.NAME_INVALID_CHARACTERS.getPhraseWithPrefix()));
                    return false;
                }

                if(server.getWorld(args[1]) != null){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.WORLD_EXISTS.getPhraseWithPrefix()));
                    return false;
                }

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                worldWrapperManager.createWorld(args[1], player);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.WORLD_CREATED.getPhraseWithPrefix()));
                return true;
            }

            case "visit": {
                if(args.length < 2){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.INSUFFICIENT_ARGUMENTS.getPhraseWithPrefix()));
                    return false;
                }

                World world = server.getWorld(args[1]);
                if(world == null){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.WORLD_DOES_NOT_EXIST.getPhraseWithPrefix()));
                    return false;
                }

                player.teleport(world.getSpawnLocation());
                return true;
            }

            case "list": {
                player.sendMessage(ChatColor.GREEN+"Worlds:");
                Iterator<WorldWrapper> worldWrapperIterator =  worldWrapperManager.getWorldWrappers().iterator();
                while(worldWrapperIterator.hasNext()){
                    WorldWrapper worldWrapper = worldWrapperIterator.next();
                    String editors = "";
                    int i = 0;
                    for(OfflinePlayer offlinePlayer : worldWrapper.getEditors()){
                        if(i > 0) editors += " ,";
                        editors += offlinePlayer.getName();
                        i++;
                    }
                    player.sendMessage(ChatColor.YELLOW+worldWrapper.getWorldName()+" - "+worldWrapper.getOwner().getName()+" ("+editors+")");
                }
                return true;
            }

            case "grant":
                if(args.length < 2){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.INSUFFICIENT_ARGUMENTS.getPhraseWithPrefix()));
                    return false;
                }
                Player target = plugin.getServer().getPlayer(args[1]);
                if(target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.CANNOT_FIND_PLAYER.getPhraseWithPrefix()));
                    return false;
                }
                if(target.equals(player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.CANNOT_FIND_PLAYER.getPhraseWithPrefix()));
                    return false;
                }

                Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
                if(worldWrapperOptional.isPresent()){
                    WorldWrapper worldWrapper = worldWrapperOptional.get();
                    if(worldWrapper.getOwner().getUniqueId().equals(player.getUniqueId())){
                        worldWrapper.changeEditor(target);
                        if(worldWrapper.getEditors().contains(target)){
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(Lang.PERMISSION_GRANTED.getPhraseWithPrefix(), target.getName())));
                        }else{
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(Lang.PERMISSION_UNGRANTED.getPhraseWithPrefix(), target.getName())));
                        }
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.NO_EDIT_ACCESS.getPhraseWithPrefix()));
                        return false;
                    }
                }
            default: {
                return false;
            }

        }

    }
}
