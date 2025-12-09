package leriz.efekt;

import leriz.efekt.command.EfektCommand;
import leriz.efekt.command.EfektTabCompleter;
import leriz.efekt.config.ConfigManager;
import leriz.efekt.database.DatabaseManager;
import leriz.efekt.listener.BlockBreakListener;
import leriz.efekt.listener.PlayerDeathListener;
import leriz.efekt.listener.PlayerJoinListener;
import leriz.efekt.listener.PlayerQuitListener;
import leriz.efekt.listener.TotemListener;
import leriz.efekt.manager.EffectManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.task.ActionBarTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class Efekt extends JavaPlugin {
    
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private PlayerDataManager playerDataManager;
    private EffectManager effectManager;
    private ActionBarTask actionBarTask;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        databaseManager = new DatabaseManager(this);
        databaseManager.connect();
        
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager(databaseManager);
        effectManager = new EffectManager(configManager);
        
        getCommand("efekt").setExecutor(new EfektCommand(this, playerDataManager, configManager, effectManager));
        getCommand("efekt").setTabCompleter(new EfektTabCompleter());
        
        getServer().getPluginManager().registerEvents(
            new BlockBreakListener(this, playerDataManager, configManager, effectManager), this);
        getServer().getPluginManager().registerEvents(
            new PlayerDeathListener(this, playerDataManager, configManager, effectManager), this);
        getServer().getPluginManager().registerEvents(
            new PlayerJoinListener(this, playerDataManager, configManager, effectManager), this);
        getServer().getPluginManager().registerEvents(
            new TotemListener(this, playerDataManager, effectManager), this);
        getServer().getPluginManager().registerEvents(
            new PlayerQuitListener(playerDataManager), this);
        
        if (configManager.shouldShowActionBarInfinitely()) {
            actionBarTask = new ActionBarTask(playerDataManager, configManager);
            actionBarTask.runTaskTimer(this, 0L, 20L);
        }
    }
    
    public void reloadPlugin() {
        if (actionBarTask != null) {
            actionBarTask.cancel();
            actionBarTask = null;
        }
        
        configManager.loadConfig();
        effectManager = new EffectManager(configManager);
        
        if (configManager.shouldShowActionBarInfinitely()) {
            actionBarTask = new ActionBarTask(playerDataManager, configManager);
            actionBarTask.runTaskTimer(this, 0L, 20L);
        }
    }
    
    @Override
    public void onDisable() {
        if (actionBarTask != null) {
            actionBarTask.cancel();
        }
        if (playerDataManager != null) {
            playerDataManager.clearAll();
        }
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
    }
}
