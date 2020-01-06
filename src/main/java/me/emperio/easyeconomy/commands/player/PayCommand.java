package me.emperio.easyeconomy.commands.player;

import me.emperio.easyeconomy.EasyEconomy;
import me.emperio.easyeconomy.Settings;
import me.emperio.easyeconomy.economy.EconomyManager;
import me.emperio.easyeconomy.lang.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private EasyEconomy plugin;
    private LangManager lang;
    private EconomyManager economyManager;

    public PayCommand(EasyEconomy plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLangManager();
        this.economyManager = plugin.getEcoManager();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if((sender instanceof Player)){
            Player player = (Player) sender;

            if(!player.hasPermission(Settings.PAY_PERMISSION)){
                player.sendMessage(lang.getValue("no-permission"));
                return true;
            }

            if(args.length != 2){
                player.sendMessage(lang.getValue("incorrect-args-command-pay"));
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if(targetPlayer == null){
                String message = lang.getValue("player-not-found")
                        .replace("{player}", args[0]);
                player.sendMessage(message);
                return true;
            }
            int amount = 0;
            try{
                amount = Integer.parseInt(args[1]);
            }
            catch(NumberFormatException e){
                player.sendMessage(lang.getValue("not-a-valid-integer"));
                return true;
            }

            if(amount > economyManager.getBalance(player)){
                player.sendMessage(lang.getValue("not-enough-money-to-pay"));
                return true;
            }

            if((economyManager.getBalance(targetPlayer) + amount) > Settings.MAX_BALANCE){

                int difference = (economyManager.getBalance(targetPlayer) + amount) - Settings.MAX_BALANCE;

                economyManager.deposit(targetPlayer.getUniqueId(), amount - difference);
                economyManager.withdraw(player.getUniqueId(), amount - difference);

                String amountString = plugin.getVaultEconomy().format(amount - difference);

                targetPlayer.sendMessage(lang.getValue("money-received")
                .replace("{amount}", amountString)
                .replace("{player}", player.getName()));

                player.sendMessage(lang.getValue("money-sent-but-player-account-got-full")
                .replace("{amount}", amountString)
                .replace("{player}", targetPlayer.getName()));

                return true;
            }

            economyManager.deposit(targetPlayer.getUniqueId(), amount);
            economyManager.withdraw(player.getUniqueId(), amount);

            String amountString = plugin.getVaultEconomy().format(amount);

            targetPlayer.sendMessage(lang.getValue("money-received")
                    .replace("{amount}", "" + amountString)
                    .replace("{player}", player.getName()));

            player.sendMessage(lang.getValue("money-sent")
                    .replace("{amount}", "" + amountString)
                    .replace("{player}", targetPlayer.getName()));

        }
        else {
            plugin.getLogger().info("You need to a player to execute that command!");
        }

        return true;

    }
}
