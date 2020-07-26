package ninja.jrc.buildtopia.listeners;

import ninja.jrc.buildtopia.managers.WorldWrapperManager;
import ninja.jrc.buildtopia.objects.WorldWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

import java.util.Optional;

public class EditInteractListener implements Listener {
    private final WorldWrapperManager worldWrapperManager;

    public EditInteractListener(WorldWrapperManager worldWrapperManager){
        this.worldWrapperManager = worldWrapperManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
        if(worldWrapperOptional.isPresent()){
            WorldWrapper worldWrapper = worldWrapperOptional.get();
            event.setCancelled(!worldWrapper.playerCanEdit(player));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
        if(worldWrapperOptional.isPresent()){
            WorldWrapper worldWrapper = worldWrapperOptional.get();
            event.setCancelled(!worldWrapper.playerCanEdit(player));
        }
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event){
        Player player = event.getPlayer();
        Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
        if(worldWrapperOptional.isPresent()){
            WorldWrapper worldWrapper = worldWrapperOptional.get();
            event.setCancelled(!worldWrapper.playerCanEdit(player));
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event){
        Player player = event.getPlayer();
        Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
        if(worldWrapperOptional.isPresent()){
            WorldWrapper worldWrapper = worldWrapperOptional.get();
            event.setCancelled(!worldWrapper.playerCanEdit(player));
        }
    }

//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event){
//        Player player = event.getPlayer();
//        Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
//        if(worldWrapperOptional.isPresent()){
//            WorldWrapper worldWrapper = worldWrapperOptional.get();
//            event.setCancelled(!worldWrapper.playerCanEdit(player));
//        }
//    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        Optional<WorldWrapper> worldWrapperOptional = worldWrapperManager.getWorld(player.getWorld());
        if(worldWrapperOptional.isPresent()){
            WorldWrapper worldWrapper = worldWrapperOptional.get();
            event.setCancelled(!worldWrapper.playerCanEdit(player));
        }
    }

}
