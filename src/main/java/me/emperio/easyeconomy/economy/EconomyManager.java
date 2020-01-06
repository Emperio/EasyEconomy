package me.emperio.easyeconomy.economy;

import me.emperio.easyeconomy.EasyEconomy;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    private HashMap<UUID, Integer> playerBalances;
    private EasyEconomy plugin;

    public EconomyManager(HashMap<UUID, Integer> playerBalances, EasyEconomy plugin) {
        this.playerBalances = playerBalances;
        this.plugin = plugin;
    }

    public void deposit(Player player, int amount){
        setBalance(player, getBalance(player) + amount);
    }

    public void deposit(UUID uuid, int amount){
        setBalance(uuid, getBalance(uuid) + amount);
    }

    public void withdraw(Player player, int amount){
        setBalance(player, getBalance(player) - amount);
    }

    public void withdraw(UUID uuid, int amount){
        setBalance(uuid, getBalance(uuid) - amount);
    }

    public int getBalance(Player player){
        return playerBalances.get(player.getUniqueId());
    }

    public int getBalance(UUID uuid){
        return playerBalances.get(uuid);
    }

    public void setBalance(Player player, int amount){
        playerBalances.put(player.getUniqueId(), amount);
        plugin.getDataConfig().set("balances." + player.getUniqueId(), amount);
        plugin.saveDataConfig();
    }

    public void setBalance(UUID uuid, int amount){
        playerBalances.put(uuid, amount);
        plugin.getDataConfig().set("balances." + uuid, amount);
        plugin.saveDataConfig();
    }


    public void createAccount(Player player, int startingAmount){
        if(hasAccount(player)) return;
        setBalance(player, startingAmount);
    }

    public void createAccount(UUID uuid, int startingAmount){
        if(hasAccount(uuid)) return;
        setBalance(uuid, startingAmount);
    }

    public boolean hasAccount(Player player){
        if(playerBalances.containsKey(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public boolean hasAccount(UUID uuid){
        if(playerBalances.containsKey(uuid)){
            return true;
        }
        return false;
    }

    public HashMap<UUID, Integer> getBalancesMap(){
        return playerBalances;
    }

}
