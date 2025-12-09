package leriz.efekt.task;

import leriz.efekt.config.ConfigManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.model.PlayerData;
import leriz.efekt.util.ActionBarUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {
    
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    
    public ActionBarTask(PlayerDataManager playerDataManager, ConfigManager configManager) {
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
    }
    
    @Override
    public void run() {
        if (!configManager.shouldShowActionBar() || !configManager.shouldShowActionBarInfinitely()) {
            return;
        }
        
        for (Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
            PlayerData data = playerDataManager.getPlayerData(player);
            
            if (!data.hasUnlockedEffects()) {
                int blocksBroken = data.getBlocksBroken();
                int requiredBlocks = configManager.getRequiredBlocks();
                String message = configManager.getProgressMessage(blocksBroken, requiredBlocks);
                ActionBarUtil.sendActionBar(player, message);
            }
        }
    }
}

