package fr.saymfire.playerManagerTemplate.manager.playerprofile;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerProfile {
    private final UUID uuid;
    private int autoSaveTaskId;

    /**
     * Get a player profile from a UUID
     * @param uuid The UUID of the player
     * @return The player profile
     */
    public static PlayerProfile from(UUID uuid) {
        PlayerProfileManager manager = PlayerProfileManager.getInstance();
        if(manager.isProfileLoaded(uuid)) {
            return manager.getProfile(uuid);
        } else {
            return new PlayerProfile(uuid);
        }
    }

    private PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        loadData();
        PlayerProfileManager.getInstance().registerProfile(this);
        if(Bukkit.getOfflinePlayer(uuid).isOnline()) {
            startAutoSaveTask();
        } else {
            PlayerProfileManager.getInstance().preUnregisterProfile(uuid);
        }
    }

    private void loadData() {
        File file = new File(PlayerProfileManager.getInstance().getPlugin().getDataFolder()+"/playerdata/"+uuid+".yml");
        if(file.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            // Load data from the file
        }
    }

    protected void saveData() {
        File file = new File(PlayerProfileManager.getInstance().getPlugin().getDataFolder()+"/playerdata/"+uuid+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        // Save data to the file
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void cancelAutoSaveTask() {
        if(autoSaveTaskId != 0) {
            Bukkit.getScheduler().cancelTask(autoSaveTaskId);
            autoSaveTaskId = 0;
        }
    }

    private void startAutoSaveTask() {
        autoSaveTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(PlayerProfileManager.getInstance().getPlugin(), this::saveData, 20*60*5, 20*60*5);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void disconnect() {
        PlayerProfileManager.getInstance().preUnregisterProfile(uuid);
    }
}
