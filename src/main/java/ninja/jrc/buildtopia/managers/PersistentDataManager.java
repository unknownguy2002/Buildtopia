package ninja.jrc.buildtopia.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ninja.jrc.buildtopia.Buildtopia;
import ninja.jrc.buildtopia.objects.WorldWrapper;
import org.bukkit.plugin.PluginLogger;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class PersistentDataManager {
    private final Gson gson = new Gson();

    private final Buildtopia plugin;
    private final Logger logger;

    // Files
    private File dataDir;
    private File worldWrappersFile;

    // Wrappers
    private Set<WorldWrapper> worldWrappers = new HashSet<>();

    public PersistentDataManager(Buildtopia plugin){
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        // Define the files
        dataDir = new File(this.plugin.getDataFolder(), "data");
        worldWrappersFile = new File(dataDir, "worldWrappers.json");

        // Make dirs and files
        if(this.plugin.getDataFolder().mkdir()){
            logger.info("No plugin directory was found, created plugin directory");
        }

        if(dataDir.mkdir()){
            logger.info("No data directory was found, created data directory");
        }

        // Check if files exist, create it if it doesn't
        try {
            if(worldWrappersFile.createNewFile()){
                logger.info(worldWrappersFile.getName()+" was not found, created it");
            }else{
                logger.info(worldWrappersFile.getName()+" was found");
            }
        } catch (IOException e) {
            logger.severe(e.toString());
            logger.severe("Error while creating file, disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
    }

    public void readData(){
        logger.info("Reading data...");
        try {
            if(!(worldWrappersFile.length() <= 0)){
                FileReader worldWrapperFileReader = new FileReader(worldWrappersFile);
                worldWrappers = gson.fromJson(worldWrapperFileReader, new TypeToken<Set<WorldWrapper>>(){}.getType());
            }else{
                logger.info(worldWrappersFile.getName()+" is empty");
            }

        } catch (FileNotFoundException e) {
            logger.severe(e.toString());
            logger.severe("Error while reading file, disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        logger.info("Finished reading data");
    }


    public void saveData(Set<WorldWrapper> worldWrappers){
        logger.info("Saving data...");

        String worldWrappersJson = gson.toJson(worldWrappers);

        try {
            FileWriter worldWrapperFileWriter = new FileWriter(worldWrappersFile);
            worldWrapperFileWriter.write(worldWrappersJson);
            worldWrapperFileWriter.close();
        } catch (IOException e) {
            logger.severe(e.toString());
            logger.severe("Error while writing to file, disabling plugin");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        logger.info("Finished saving data");
    }

    // Getters
    public Set<WorldWrapper> getWorldWrappers() {
        return worldWrappers;
    }
}
