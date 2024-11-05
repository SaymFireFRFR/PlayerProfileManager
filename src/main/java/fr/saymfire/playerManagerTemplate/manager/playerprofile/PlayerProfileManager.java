package fr.saymfire.playerManagerTemplate.manager.playerprofile;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileManager {
    private static PlayerProfileManager instance;
    private final JavaPlugin plugin;
    private final Map<UUID, PlayerProfile> profiles = new HashMap<>();
    private final Map<UUID, Integer> profileUnregisteringTaskId = new HashMap<>();

    public PlayerProfileManager(JavaPlugin plugin) {
        if(instance != null) {
            throw new IllegalStateException("PlayerManager is already initialized");
        }
        this.plugin = plugin;
        instance = this;
    }

    public boolean isProfileLoaded(UUID uuid) {
        return profiles.containsKey(uuid);
    }

    public boolean isProfileUnregistering(UUID uuid) {
        return profileUnregisteringTaskId.containsKey(uuid);
    }

    protected void registerProfile(PlayerProfile profile) {
        profiles.put(profile.getUuid(), profile);
    }

    protected void preUnregisterProfile(UUID uuid) {
        profileUnregisteringTaskId.put(uuid, plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> unregisterProfile(uuid), 20 * 60*5));
        plugin.getLogger().info("Profile for " + uuid + " will be unregistered in 5 minutes");
    }

    private void unregisterProfile(UUID uuid) {
        PlayerProfile profile = profiles.get(uuid);
        profile.cancelAutoSaveTask();
        profile.saveData();
        profiles.remove(uuid);
        plugin.getLogger().info("Unregistered profile for " + uuid);
    }

    private void cancelUnregisterProfile(UUID uuid) {
        plugin.getServer().getScheduler().cancelTask(profileUnregisteringTaskId.get(uuid));
        profileUnregisteringTaskId.remove(uuid);
        plugin.getLogger().info("Unregistering of profile for " + uuid + " has been cancelled");
    }

    protected PlayerProfile getProfile(UUID uuid) {
        if(!isProfileLoaded(uuid)) {
            throw new IllegalArgumentException("Profile not loaded");
        }
        if(isProfileUnregistering(uuid)) {
            cancelUnregisterProfile(uuid);
        }
        return profiles.get(uuid);
    }

    public static PlayerProfileManager getInstance() {
        return instance;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
