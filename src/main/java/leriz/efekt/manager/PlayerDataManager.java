package leriz.efekt.manager;

import leriz.efekt.database.DatabaseManager;
import leriz.efekt.model.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    
    private final Map<UUID, PlayerData> playerDataMap;
    private final DatabaseManager databaseManager;
    
    public PlayerDataManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.playerDataMap = new HashMap<>();
    }
    
    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }
    
    public PlayerData getPlayerData(UUID uuid) {
        if (playerDataMap.containsKey(uuid)) {
            return playerDataMap.get(uuid);
        }
        
        PlayerData data = databaseManager.loadPlayerData(uuid);
        playerDataMap.put(uuid, data);
        return data;
    }
    
    public void savePlayerData(PlayerData data) {
        databaseManager.savePlayerData(data);
    }
    
    public void removePlayerData(UUID uuid) {
        PlayerData data = playerDataMap.remove(uuid);
        if (data != null) {
            savePlayerData(data);
        }
    }
    
    public void clearAll() {
        for (PlayerData data : playerDataMap.values()) {
            savePlayerData(data);
        }
        playerDataMap.clear();
    }
    
    public void resetPlayerData(UUID uuid) {
        databaseManager.resetPlayerData(uuid);
        PlayerData data = new PlayerData(uuid);
        playerDataMap.put(uuid, data);
    }
}

