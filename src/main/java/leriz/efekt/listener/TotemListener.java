package leriz.efekt.listener;

import leriz.efekt.Efekt;
import leriz.efekt.manager.EffectManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.model.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class TotemListener implements Listener {
    
    private final Efekt plugin;
    private final PlayerDataManager playerDataManager;
    private final EffectManager effectManager;
    
    public TotemListener(Efekt plugin, PlayerDataManager playerDataManager, EffectManager effectManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.effectManager = effectManager;
    }
    
    @EventHandler
    public void onTotemActivate(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        
        Player player = (Player) event.getEntity();
        PlayerData data = playerDataManager.getPlayerData(player);
        
        if (data.hasUnlockedEffects()) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (player.isOnline()) {
                    effectManager.applyEffects(player);
                }
            }, 1L);
        }
    }
}

