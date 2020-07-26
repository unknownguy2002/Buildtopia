package ninja.jrc.buildtopia;

import ninja.jrc.buildtopia.commands.BaseCommand;
import ninja.jrc.buildtopia.commands.BuildCommand;
import ninja.jrc.buildtopia.listeners.EditInteractListener;
import ninja.jrc.buildtopia.listeners.PlayerTeleportListener;
import ninja.jrc.buildtopia.managers.PersistentDataManager;
import ninja.jrc.buildtopia.managers.WorldWrapperManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;
import java.util.logging.Logger;

public final class Buildtopia extends JavaPlugin {
    Logger logger = getLogger();
    PersistentDataManager persistentDataManager;
    WorldWrapperManager worldWrapperManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Initialing plugin");

        this.persistentDataManager = new PersistentDataManager(this);
        this.persistentDataManager.readData();

        this.worldWrapperManager = new WorldWrapperManager(this, this.persistentDataManager.getWorldWrappers());


        setupCommands();
        setupListeners();
        setupTasks();
    }

    private void setupCommands(){
        // Base Command
        PluginCommand basePluginCommand = getCommand("buildtopia");
        assert basePluginCommand != null;
        basePluginCommand.setExecutor(new BaseCommand(this));
        basePluginCommand.setAliases(Arrays.asList("bt", "buildt", "btopia"));

        // Build Command
        PluginCommand buildPluginCommand = getCommand("build");
        assert buildPluginCommand != null;
        buildPluginCommand.setExecutor(new BuildCommand(this, worldWrapperManager));
        buildPluginCommand.setAliases(Arrays.asList("b"));

    }

    private void setupListeners(){
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new EditInteractListener(worldWrapperManager), this);
        pluginManager.registerEvents(new PlayerTeleportListener(worldWrapperManager), this);
    }

    private void setupTasks(){
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            persistentDataManager.saveData(worldWrapperManager.getWorldWrappers());
        }, 0L, 6000L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        persistentDataManager.saveData(worldWrapperManager.getWorldWrappers());
    }
}
