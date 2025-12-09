package leriz.efekt.database;

import leriz.efekt.Efekt;
import leriz.efekt.model.PlayerData;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    
    private final Efekt plugin;
    private Connection connection;
    
    public DatabaseManager(Efekt plugin) {
        this.plugin = plugin;
    }
    
    public void connect() {
        try {
            String url = "jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/data.db";
            connection = DriverManager.getConnection(url);
            
            createTable();
        } catch (SQLException e) {
            plugin.getLogger().severe("Veritabanı bağlantısı kurulamadı: " + e.getMessage());
        }
    }
    
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_data (" +
                "uuid TEXT PRIMARY KEY, " +
                "blocks_broken INTEGER DEFAULT 0, " +
                "has_unlocked_effects INTEGER DEFAULT 0" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            plugin.getLogger().severe("Tablo oluşturulamadı: " + e.getMessage());
        }
    }
    
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Veritabanı bağlantısı kapatılamadı: " + e.getMessage());
        }
    }
    
    public PlayerData loadPlayerData(UUID uuid) {
        String sql = "SELECT blocks_broken, has_unlocked_effects FROM player_data WHERE uuid = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                PlayerData data = new PlayerData(uuid);
                data.setBlocksBroken(rs.getInt("blocks_broken"));
                data.setUnlockedEffects(rs.getInt("has_unlocked_effects") == 1);
                return data;
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Oyuncu verisi yüklenemedi: " + e.getMessage());
        }
        
        return new PlayerData(uuid);
    }
    
    public void savePlayerData(PlayerData data) {
        String sql = "INSERT OR REPLACE INTO player_data (uuid, blocks_broken, has_unlocked_effects) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, data.getPlayerId().toString());
            pstmt.setInt(2, data.getBlocksBroken());
            pstmt.setInt(3, data.hasUnlockedEffects() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Oyuncu verisi kaydedilemedi: " + e.getMessage());
        }
    }
    
    public void resetPlayerData(UUID uuid) {
        String sql = "UPDATE player_data SET blocks_broken = 0, has_unlocked_effects = 0 WHERE uuid = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Oyuncu verisi sıfırlanamadı: " + e.getMessage());
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
}

