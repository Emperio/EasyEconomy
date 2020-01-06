package me.emperio.easyeconomy.commands;

import me.emperio.easyeconomy.EasyEconomy;
import me.emperio.easyeconomy.Settings;
import me.emperio.easyeconomy.lang.LangManager;
import me.emperio.easyeconomy.util.EmpUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("Duplicates")
public class EconomyMasterCommand implements CommandExecutor {

    /*
        Note to Developers looking to edit/modify/look into this:
            Beware that half of the code here is somewhat messy,
            and needs considerable clean-up.
     */

    private EasyEconomy plugin;
    private LangManager lang;

    public EconomyMasterCommand(EasyEconomy plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLangManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        if ((sender instanceof Player)) {
            Player player = (Player) sender;

            if(!player.hasPermission(Settings.ADMIN_PERMISSION)){
                player.sendMessage(lang.getValue("no-permission"));
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(EmpUtil.getColored("&cPlease specify a sub-command to &a/" + command.getName()));
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {

                if (args.length != 3) {
                    player.sendMessage(EmpUtil.getColored("&cInvalid Args. Correct Usage: &a/" + command.getName() +
                            " set <player> <balance>"));
                    return true;
                }

                OfflinePlayer targetPlayer = plugin.getServer().getOfflinePlayer(args[1]);
                if (!targetPlayer.hasPlayedBefore() && !targetPlayer.isOnline()) {
                    player.sendMessage(lang.getValue("player-not-played-before")
                    .replace("{player}", args[1]));
                    return true;
                }

                int newBalance = 0;
                try {
                    newBalance = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(lang.getValue("invalid-integer"));
                    return true;
                }

                if (newBalance > Settings.MAX_BALANCE) {
                    player.sendMessage(EmpUtil.getColored("&cThe new balance provided is larger than the max balance!"));
                    return true;
                }

                plugin.getEcoManager().setBalance(targetPlayer.getUniqueId(), newBalance);
                player.sendMessage(EmpUtil.getColored("&aUpdated player &c" + targetPlayer.getName() + "'s&a balance to &c"
                        + Settings.CURRENCY_PREFIX + plugin.economyImplementation.format(newBalance)));

                return true;
            }


            else if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) {

                if (args.length != 3) {
                    player.sendMessage(EmpUtil.getColored("&cInvalid Args. Correct Usage: &a/" + command.getName() +
                            " give,add <player> <amount>"));
                    return true;
                }

                OfflinePlayer targetPlayer = plugin.getServer().getOfflinePlayer(args[1]);
                if (!targetPlayer.hasPlayedBefore() && !targetPlayer.isOnline()) {
                    player.sendMessage(lang.getValue("player-not-played-before")
                    .replace("{player}", args[1]));
                    return true;
                }

                int amount = 0;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(lang.getValue("not-a-valid-integer"));
                    return true;
                }

                if ((plugin.getEcoManager().getBalance(targetPlayer.getUniqueId()) + amount) > Settings.MAX_BALANCE) {
                    int difference = (plugin.getEcoManager().getBalance(targetPlayer.getUniqueId()) + amount) - Settings.MAX_BALANCE;
                    plugin.getEcoManager().deposit(targetPlayer.getUniqueId(), amount - difference);


                    player.sendMessage(EmpUtil.getColored("&aAdded only &c" + Settings.CURRENCY_PREFIX + plugin.economyImplementation.format(amount - difference) + "&a to player &c" + targetPlayer.getName() + "'s&a account as their account had reached max balance"));

                    Player oPlayer = Bukkit.getPlayer(targetPlayer.getUniqueId());

                    if(oPlayer != null){
                        oPlayer.sendMessage(lang.getValue("money-added-to-account")
                                .replace("{amount}", plugin.economyImplementation.format(amount)));
                    }


                    return true;
                }

                plugin.getEcoManager().deposit(targetPlayer.getUniqueId(), amount);
                player.sendMessage(EmpUtil.getColored("&aAdded &c" + Settings.CURRENCY_PREFIX + plugin.economyImplementation.format((amount)) + "&a to player &c" + targetPlayer.getName() + "'s&a account."));

                Player oPlayer = Bukkit.getPlayer(targetPlayer.getUniqueId());

                if(oPlayer != null){
                    oPlayer.sendMessage(lang.getValue("money-added-to-account")
                    .replace("{amount}", plugin.economyImplementation.format(amount)));
                }


            }
            else if (args[0].equalsIgnoreCase("reset")){

                if(args.length != 2){
                    player.sendMessage(EmpUtil.getColored("&cIncorrect Args. Correct Usage: &a/eco reset <balance>"));
                    return true;
                }

                int newBalance = 0;
                try{
                     newBalance = Integer.parseInt(args[1]);
                }
                catch(NumberFormatException e){
                    player.sendMessage(lang.getValue("not-a-valid-integer"));
                    return true;
                }

                int x = 0;
                for(UUID each : plugin.getEcoManager().getBalancesMap().keySet()){
                    plugin.getEcoManager().setBalance(each, newBalance);
                    x++;
                }
                player.sendMessage(EmpUtil.getColored("&aReset balance for &c" + x + "&a players"));

            }
            else if (args[0].equalsIgnoreCase("help")){

                player.sendMessage(EmpUtil.getColored("&a--------+&c/eco help&a+--------"));
                player.sendMessage(EmpUtil.getColored("&b/eco set <player> <balance>: "));
                player.sendMessage(EmpUtil.getColored("  &c» &a&oSets a players balance"));
                player.sendMessage(EmpUtil.getColored("&b/eco give <player> <amount>: "));
                player.sendMessage(EmpUtil.getColored("  &c» &a&oAdds the specified amount to the player's account"));
                player.sendMessage(EmpUtil.getColored("&b/eco reset <balance>: "));
                player.sendMessage(EmpUtil.getColored("  &c» &a&oResets the balance of ALL players to the specified amount"));
                player.sendMessage(EmpUtil.getColored("&b/eco reload: "));
                player.sendMessage(EmpUtil.getColored("  &c» &a&oReloads EasyEconomy"));
                player.sendMessage(EmpUtil.getColored("&a-------------------------"));

            }
            else if(args[0].equalsIgnoreCase("reload")){
                player.sendMessage(EmpUtil.getColored("&aAttempting to reload &cEasyEconomy..."));
                plugin.reload();
                player.sendMessage(EmpUtil.getColored("&cEasyEconomy &aSucessfully Reloaded!"));
            }



        }


        return true;
    }

}
