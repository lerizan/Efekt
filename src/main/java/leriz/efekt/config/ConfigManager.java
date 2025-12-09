package leriz.efekt.config;

import leriz.efekt.Efekt;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigManager {
    
    private final Efekt plugin;
    private int requiredBlocks;
    private final Map<PotionEffectType, Integer> effects;
    private String progressMessage;
    private String activatedMessage;
    private String titleBaslik;
    private String titleAltBaslik;
    private String respawnTitleBaslik;
    private String respawnTitleAltBaslik;
    private boolean showActionBar;
    private boolean showActionBarInfinitely;
    private boolean countSpecificBlocks;
    private Set<Material> allowedBlocks;
    private Sound effectSound;
    private float soundVolume;
    
    public ConfigManager(Efekt plugin) {
        this.plugin = plugin;
        this.effects = new HashMap<>();
        this.allowedBlocks = new HashSet<>();
        loadConfig();
    }
    
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        
        requiredBlocks = config.getInt("gerekli-bloklar", 1200);
        
        effects.clear();
        List<Map<?, ?>> effectList = config.getMapList("efektler");
        for (Map<?, ?> effectMap : effectList) {
            String typeName = (String) effectMap.get("tip");
            Integer level = ((Number) effectMap.get("seviye")).intValue();
            
            try {
                PotionEffectType type = PotionEffectType.getByName(typeName);
                if (type != null) {
                    effects.put(type, level);
                }
            } catch (Exception ignored) {
            }
        }
        
        progressMessage = config.getString("mesajlar.ilerleme", "&eBüyüleri almak için blok kır! &7{bloklar}/{gerekli}");
        activatedMessage = config.getString("mesajlar.aktif-edildi", "&aBüyüler aktif edildi");
        titleBaslik = config.getString("mesajlar.title-baslik", "&aBaşarılı!");
        titleAltBaslik = config.getString("mesajlar.title-alt-baslik", "&7Sonunda {gerekli} blok kırdığın için büyüleri alabiliceksin");
        respawnTitleBaslik = config.getString("mesajlar.respawn-title-baslik", "&eBüyü");
        respawnTitleAltBaslik = config.getString("mesajlar.respawn-title-alt-baslik", "&cÖldüğün için büyüleri tekrar aldın");
        
        showActionBar = config.getBoolean("ayarlar.actionbar-goster", true);
        showActionBarInfinitely = config.getBoolean("ayarlar.actionbar-sonsuz-goster", true);
        countSpecificBlocks = config.getBoolean("ayarlar.belirli-bloklar-say", false);
        
        String soundName = config.getString("sesler.efekt-alindi", "ENTITY_PLAYER_LEVELUP");
        try {
            effectSound = Sound.valueOf(soundName);
        } catch (Exception e) {
            effectSound = Sound.ENTITY_PLAYER_LEVELUP;
        }
        soundVolume = (float) config.getDouble("sesler.ses-seviyesi", 1.0);
        
        allowedBlocks.clear();
        if (countSpecificBlocks) {
            List<String> blockNames = config.getStringList("ayarlar.sayilacak-bloklar");
            for (String blockName : blockNames) {
                try {
                    Material material = Material.valueOf(blockName.toUpperCase());
                    allowedBlocks.add(material);
                } catch (Exception ignored) {
                }
            }
        }
    }
    
    public int getRequiredBlocks() {
        return requiredBlocks;
    }
    
    public Map<PotionEffectType, Integer> getEffects() {
        return new HashMap<>(effects);
    }
    
    public String getProgressMessage(int blocksBroken, int required) {
        return progressMessage.replace("{bloklar}", String.valueOf(blocksBroken))
                .replace("{gerekli}", String.valueOf(required));
    }
    
    public String getActivatedMessage() {
        return activatedMessage;
    }
    
    public boolean shouldShowActionBar() {
        return showActionBar;
    }
    
    public boolean shouldShowActionBarInfinitely() {
        return showActionBarInfinitely;
    }
    
    public boolean shouldCountSpecificBlocks() {
        return countSpecificBlocks;
    }
    
    public boolean isBlockAllowed(Material material) {
        if (!countSpecificBlocks) {
            return true;
        }
        return allowedBlocks.contains(material);
    }
    
    public String getTitleBaslik() {
        return titleBaslik;
    }
    
    public String getTitleAltBaslik() {
        return titleAltBaslik.replace("{gerekli}", String.valueOf(requiredBlocks));
    }
    
    public Sound getEffectSound() {
        return effectSound;
    }
    
    public float getSoundVolume() {
        return soundVolume;
    }
    
    public String getRespawnTitleBaslik() {
        return respawnTitleBaslik;
    }
    
    public String getRespawnTitleAltBaslik() {
        return respawnTitleAltBaslik;
    }
}

