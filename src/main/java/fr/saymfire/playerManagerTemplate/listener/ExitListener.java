package fr.saymfire.playerManagerTemplate.listener;

import fr.saymfire.playerManagerTemplate.manager.playerprofile.PlayerProfile;
import fr.saymfire.playerManagerTemplate.manager.playerprofile.PlayerProfileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ExitListener implements Listener {
    @EventHandler
    public void onExit(PlayerQuitEvent e) {
        PlayerProfile.from(e.getPlayer().getUniqueId()).disconnect();
    }
}
