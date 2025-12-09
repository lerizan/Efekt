package leriz.efekt.listener;

import leriz.efekt.Efekt;
import leriz.efekt.config.ConfigManager;
import leriz.efekt.manager.EffectManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.model.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    
    private final Efekt plugin;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    private final EffectManager effectManager;
    
    public PlayerJoinListener(Efekt plugin, PlayerDataManager playerDataManager, ConfigManager configManager, EffectManager effectManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
        this.effectManager = effectManager;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = playerDataManager.getPlayerData(player);
        
        if (data.hasUnlockedEffects()) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (player.isOnline()) {
                    effectManager.applyEffects(player);
                }
            }, 20L);
        }
    }
}

