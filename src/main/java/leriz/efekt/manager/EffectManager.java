package leriz.efekt.manager;

import leriz.efekt.config.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class EffectManager {
    
    private final ConfigManager configManager;
    
    public EffectManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    public void applyEffects(Player player) {
        Map<PotionEffectType, Integer> effects = configManager.getEffects();
        
        for (Map.Entry<PotionEffectType, Integer> entry : effects.entrySet()) {
            PotionEffectType type = entry.getKey();
            int level = entry.getValue();
            
            PotionEffect effect = new PotionEffect(type, Integer.MAX_VALUE, level - 1, true, false);
            player.addPotionEffect(effect);
        }
        
        Sound sound = configManager.getEffectSound();
        float volume = configManager.getSoundVolume();
        player.playSound(player.getLocation(), sound, volume, 1.0f);
    }
    
    public void removeEffects(Player player) {
        Map<PotionEffectType, Integer> effects = configManager.getEffects();
        
        for (PotionEffectType type : effects.keySet()) {
            player.removePotionEffect(type);
        }
    }
}

