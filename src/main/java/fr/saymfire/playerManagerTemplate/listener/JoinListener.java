package fr.saymfire.playerManagerTemplate.listener;

import fr.saymfire.playerManagerTemplate.manager.playerprofile.PlayerProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerProfile profile = PlayerProfile.from(e.getPlayer().getUniqueId());
    }
}
