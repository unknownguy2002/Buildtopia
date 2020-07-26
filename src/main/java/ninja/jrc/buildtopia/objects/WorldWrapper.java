package ninja.jrc.buildtopia.objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WorldWrapper {
    private final String worldName;
    private transient World world;

    private UUID ownerUUID;
    private transient OfflinePlayer owner;

    private final Set<UUID> editorsUUID = new HashSet<>();
    private transient Set<OfflinePlayer> editors = new HashSet<>();

    public WorldWrapper(World world, OfflinePlayer owner, Set<OfflinePlayer> editors){
        this.worldName = world.getName();
        this.world = world;
        this.ownerUUID = owner.getUniqueId();
        this.owner = owner;
        this.editors = editors;
        for(OfflinePlayer offlinePlayer : editors){
            editorsUUID.add(offlinePlayer.getUniqueId());
        }
    }

    public String getWorldName() {
        return worldName;
    }
    public World getWorld() {
        return world;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    public OfflinePlayer getOwner() {
        return owner;
    }
    public void setOwner(Player owner) {
        this.ownerUUID = owner.getUniqueId();
        this.owner = owner;
    }

    public Set<UUID> getEditorsUUID() {
        return editorsUUID;
    }
    public Set<OfflinePlayer> getEditors() {
        return editors;
    }
    public void changeEditor(OfflinePlayer player){
        if(editors.contains(player)){
            editors.remove(player);
            editorsUUID.remove(player.getUniqueId());
        }else{
            editors.add(player);
            editorsUUID.add(player.getUniqueId());
        }
    }

    public boolean playerCanEdit(OfflinePlayer player){
        return editorsUUID.contains(player.getUniqueId()) || ownerUUID.equals(player.getUniqueId());
    }

}
