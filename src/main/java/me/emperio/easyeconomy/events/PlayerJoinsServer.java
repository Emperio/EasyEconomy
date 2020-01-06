package me.emperio.easyeconomy.events;

import me.emperio.easyeconomy.EasyEconomy;
import me.emperio.easyeconomy.Settings;
import me.emperio.easyeconomy.economy.EconomyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinsServer implements Listener {

    private EasyEconomy plugin;
    private EconomyManager economyManager;

    public PlayerJoinsServer(EasyEconomy plugin) {
        this.plugin = plugin;
        this.economyManager = plugin.getEcoManager();
    }

    @EventHandler
    public void playerJoinsServer(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(!economyManager.hasAccount(player)){
            economyManager.createAccount(player, Settings.DEFAULT_BAL);
        }

    }

}
