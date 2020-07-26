package ninja.jrc.buildtopia.listeners;

import ninja.jrc.buildtopia.managers.WorldWrapperManager;
import ninja.jrc.buildtopia.objects.WorldWrapper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Optional;

public class PlayerTeleportListener implements Listener {
    private final WorldWrapperManager worldWrapperManager;

    public PlayerTeleportListener(WorldWrapperManager worldWrapperManager){
        this.worldWrapperManager = worldWrapperManager;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();


        if(!from.getWorld().equals(to.getWorld())){
            // If it is null urm... halp I guess
            if(to.getWorld() == null) return;
            Optional<WorldWrapper> optionalWorldWrapper = worldWrapperManager.getWorld(to.getWorld());
            if(optionalWorldWrapper.isPresent()){
                WorldWrapper worldWrapper = optionalWorldWrapper.get();
                player.sendTitle(to.getWorld().getName(), "Owned by: "+worldWrapper.getOwner().getName(), 5, 50, 5);
            }
        }
    }

}
