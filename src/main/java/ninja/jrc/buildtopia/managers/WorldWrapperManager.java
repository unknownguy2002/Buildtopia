package ninja.jrc.buildtopia.managers;

import ninja.jrc.buildtopia.Buildtopia;
import ninja.jrc.buildtopia.objects.WorldWrapper;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class WorldWrapperManager {
    private final Buildtopia plugin;
    private final Server server;

    private final Set<WorldWrapper> worldWrappers = new HashSet<>();

    public WorldWrapperManager(Buildtopia plugin, Set<WorldWrapper> WorldWrappers){
        this.plugin = plugin;
        this.server = plugin.getServer();

        // Set worldWrappers with transient fields
        Iterator<WorldWrapper> worldWrapperIterator = WorldWrappers.iterator();
        while(worldWrapperIterator.hasNext()){
            // Add worldWrapper to set
            WorldWrapper worldWrapperIterated = worldWrapperIterator.next();

            // Load world
            plugin.getServer().createWorld(new WorldCreator(worldWrapperIterated.getWorldName()));

            OfflinePlayer worldOwner = plugin.getServer().getOfflinePlayer(worldWrapperIterated.getOwnerUUID());
            World world = plugin.getServer().getWorld(worldWrapperIterated.getWorldName());
            Set<OfflinePlayer> editors = new HashSet<>();
            for(UUID editorUUID : worldWrapperIterated.getEditorsUUID()){
                editors.add(plugin.getServer().getOfflinePlayer(editorUUID));
            }

            WorldWrapper worldWrapper = new WorldWrapper(world, worldOwner, editors);
            this.worldWrappers.add(worldWrapper);

        }
    }

    public Set<WorldWrapper> getWorldWrappers() {
        return worldWrappers;
    }

    public WorldWrapper createWorld(String worldName, Player owner){
        if(worldName.length() > 15){
            throw new IllegalArgumentException("Cannot name a world with more than 15 characters");
        }

        if(!worldName.matches("^[A-Za-z0-9_-]*$")){
            throw new IllegalArgumentException("World names can only contain A-Z, a-z, 0-9 and underscores");
        }

        if(server.getWorld(worldName) != null){
            throw new IllegalArgumentException("Cannot create a world that exists");
        }

        plugin.getLogger().info(worldName+" - Creating world");
        WorldCreator wc = new WorldCreator(worldName);
        wc.type(WorldType.FLAT);
        World world = wc.createWorld(); // So this will return a world if world exists, no need to null check???
        plugin.getLogger().info(worldName+" - Setting & configuring world border");
        world.setMonsterSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setDifficulty(Difficulty.PEACEFUL);
        WorldBorder border = world.getWorldBorder();
        border.setSize(400);
        border.setCenter(0, 0);

        WorldWrapper worldWrapper = new WorldWrapper(world, owner, new HashSet<>());
        worldWrappers.add(worldWrapper);

        plugin.getLogger().info("World, "+worldName+" created!");
        return worldWrapper;
    }


    public Optional<WorldWrapper> getWorld(String name){
        for(WorldWrapper worldWrapper : worldWrappers){
            if(worldWrapper.getWorldName().equals(name)){
                return Optional.of(worldWrapper);
            }
        }
        return Optional.empty();
    }

    public Optional<WorldWrapper> getWorld(World world){
        for(WorldWrapper worldWrapper : worldWrappers){
            if(worldWrapper.getWorld().getName().equals(world.getName())){
                return Optional.of(worldWrapper);
            }
        }
        return Optional.empty();
    }


}
