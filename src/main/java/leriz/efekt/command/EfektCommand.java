package leriz.efekt.command;

import leriz.efekt.Efekt;
import leriz.efekt.config.ConfigManager;
import leriz.efekt.manager.EffectManager;
import leriz.efekt.manager.PlayerDataManager;
import leriz.efekt.model.PlayerData;
import leriz.efekt.util.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EfektCommand implements CommandExecutor {
    
    private final Efekt plugin;
    private final PlayerDataManager playerDataManager;
    private final ConfigManager configManager;
    private final EffectManager effectManager;
    
    public EfektCommand(Efekt plugin, PlayerDataManager playerDataManager, ConfigManager configManager, EffectManager effectManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.configManager = configManager;
        this.effectManager = effectManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("efekt.admin")) {
            sender.sendMessage(ChatColor.RED + "Bu komutu kullanmak için yetkiniz yok!");
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /efekt <ver|sıfırla|reload> [oyuncu]");
            return true;
        }
        
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPlugin();
            sender.sendMessage(ChatColor.GREEN + "Config başarıyla yeniden yüklendi!");
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Oyuncu adı belirtmelisiniz!");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Oyuncu bulunamadı");
            return true;
        }
        
        if (args[0].equalsIgnoreCase("ver")) {
            PlayerData data = playerDataManager.getPlayerData(target);
            data.setBlocksBroken(configManager.getRequiredBlocks());
            data.setUnlockedEffects(true);
            playerDataManager.savePlayerData(data);
            
            effectManager.applyEffects(target);
            TitleUtil.sendTitle(target, configManager.getTitleBaslik(), configManager.getTitleAltBaslik(), 10, 70, 20);
            
            sender.sendMessage(ChatColor.GREEN + target.getName() + " oyuncusuna efektler verildi!");
            target.sendMessage(ChatColor.GREEN + "Admin tarafından size efektler verildi!");
            
        } else if (args[0].equalsIgnoreCase("sıfırla")) {
            playerDataManager.resetPlayerData(target.getUniqueId());
            effectManager.removeEffects(target);
            
            sender.sendMessage(ChatColor.GREEN + target.getName() + " oyuncusunun ilerlemesi sıfırlandı!");
            target.sendMessage(ChatColor.YELLOW + "İlerlemeniz admin tarafından sıfırlandı. Tekrar " + configManager.getRequiredBlocks() + " blok kırmanız gerekiyor.");
            
        } else {
            sender.sendMessage(ChatColor.RED + "Kullanım: /efekt <ver|sıfırla|reload> [oyuncu]");
        }
        
        return true;
    }
}

