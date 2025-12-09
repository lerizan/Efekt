package leriz.efekt.listener;

import leriz.efekt.manager.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    
    private final PlayerDataManager playerDataManager;
    
    public PlayerQuitListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerDataManager.removePlayerData(event.getPlayer().getUniqueId());
    }
}

