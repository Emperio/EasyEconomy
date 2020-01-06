package me.emperio.easyeconomy.util;

import me.emperio.easyeconomy.EasyEconomy;
import org.bukkit.ChatColor;

public class EmpUtil {

    public static String getColored(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void logMessage(String message){
        EasyEconomy.getPlugin(EasyEconomy.class).getServer().getConsoleSender().sendMessage("[EasyEconomy] " +
                getColored(message));
    }


}
