package leriz.efekt.listener;

import leriz.efekt.Efekt;
import leriz.efekt.config.ConfigManager;
import leriz.efekt.manager.EffectManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.model.PlayerData;
import leriz.efekt.util.TitleUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {
    
    private final Efekt plugin;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    private final EffectManager effectManager;
    
    public PlayerDeathListener(Efekt plugin, PlayerDataManager playerDataManager, ConfigManager configManager, EffectManager effectManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
        this.effectManager = effectManager;
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData data = playerDataManager.getPlayerData(player);
        
        if (data.hasUnlockedEffects()) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (player.isOnline()) {
                    effectManager.applyEffects(player);
                    TitleUtil.sendTitle(player, configManager.getRespawnTitleBaslik(), configManager.getRespawnTitleAltBaslik(), 10, 70, 20);
                }
            }, 5L);
        }
    }
}

