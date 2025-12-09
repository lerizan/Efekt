package leriz.efekt.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleUtil {
    
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        String coloredTitle = ChatColor.translateAlternateColorCodes('&', title);
        String coloredSubtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        player.sendTitle(coloredTitle, coloredSubtitle, fadeIn, stay, fadeOut);
    }
}

