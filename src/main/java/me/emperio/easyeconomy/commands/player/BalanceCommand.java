package me.emperio.easyeconomy.commands.player;

import me.emperio.easyeconomy.EasyEconomy;
import me.emperio.easyeconomy.Settings;
import me.emperio.easyeconomy.economy.EconomyManager;
import me.emperio.easyeconomy.lang.LangManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private EasyEconomy plugin;
    private EconomyManager economyManager;
    private LangManager lang;

    public BalanceCommand(EasyEconomy plugin) {
        this.plugin = plugin;
        this.economyManager = plugin.getEcoManager();
        this.lang = plugin.getLangManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if((sender instanceof Player)) {
            Player player = (Player) sender;

            if(!player.hasPermission(Settings.BALANCE_PERMISSION)){
                player.sendMessage(lang.getValue("no-permission"));
                return true;
            }

            if(args.length > 0){
                OfflinePlayer targetPlayer = plugin.getServer().getOfflinePlayer(args[0]);
                if(!targetPlayer.hasPlayedBefore() && !targetPlayer.isOnline()){
                    player.sendMessage(lang.getValue("player-not-played-before")
                    .replace("{player}", args[0]));
                    return true;
                }

                player.sendMessage(lang.getValue("balance-message")
                .replace("{player}", args[0])
                .replace("{balance}", "" +
                        plugin.economyImplementation.format(economyManager.getBalance(targetPlayer.getUniqueId()))));
                return true;
            }

            player.sendMessage(lang.getValue("balance-message")
                    .replace("{player}", player.getName())
                    .replace("{balance}", "" +
                            plugin.economyImplementation.format(economyManager.getBalance(player.getUniqueId()))));

        }
        return true;

    }
}
