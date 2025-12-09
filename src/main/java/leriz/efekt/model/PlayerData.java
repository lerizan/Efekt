package leriz.efekt.model;

import java.util.UUID;

public class PlayerData {
    
    private final UUID playerId;
    private int blocksBroken;
    private boolean hasUnlockedEffects;
    
    public PlayerData(UUID playerId) {
        this.playerId = playerId;
        this.blocksBroken = 0;
        this.hasUnlockedEffects = false;
    }
    
    public UUID getPlayerId() {
        return playerId;
    }
    
    public int getBlocksBroken() {
        return blocksBroken;
    }
    
    public void setBlocksBroken(int blocksBroken) {
        this.blocksBroken = blocksBroken;
    }
    
    public void addBlock() {
        this.blocksBroken++;
    }
    
    public boolean hasUnlockedEffects() {
        return hasUnlockedEffects;
    }
    
    public void setUnlockedEffects(boolean hasUnlockedEffects) {
        this.hasUnlockedEffects = hasUnlockedEffects;
    }
}

