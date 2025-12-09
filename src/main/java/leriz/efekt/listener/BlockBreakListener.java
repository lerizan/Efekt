package leriz.efekt.listener;

import leriz.efekt.Efekt;
import leriz.efekt.config.ConfigManager;
import leriz.efekt.manager.EffectManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.model.PlayerData;
import leriz.efekt.util.ActionBarUtil;
import leriz.efekt.util.TitleUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    
    private final Efekt plugin;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    private final EffectManager effectManager;
    
    public BlockBreakListener(Efekt plugin, PlayerDataManager playerDataManager, ConfigManager configManager, EffectManager effectManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
        this.effectManager = effectManager;
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData data = playerDataManager.getPlayerData(player);
        
        if (data.hasUnlockedEffects()) {
            return;
        }
        
        if (!configManager.isBlockAllowed(event.getBlock().getType())) {
            return;
        }
        
        data.addBlock();
        playerDataManager.savePlayerData(data);
        
        int blocksBroken = data.getBlocksBroken();
        int requiredBlocks = configManager.getRequiredBlocks();
        
        if (blocksBroken >= requiredBlocks) {
            data.setUnlockedEffects(true);
            playerDataManager.savePlayerData(data);
            effectManager.applyEffects(player);
            
            TitleUtil.sendTitle(player, configManager.getTitleBaslik(), configManager.getTitleAltBaslik(), 10, 70, 20);
        } else {
            if (configManager.shouldShowActionBar()) {
                String message = configManager.getProgressMessage(blocksBroken, requiredBlocks);
                ActionBarUtil.sendActionBar(player, message);
            }
        }
    }
}

