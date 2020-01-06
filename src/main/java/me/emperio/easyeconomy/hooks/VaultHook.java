package me.emperio.easyeconomy.hooks;

import me.emperio.easyeconomy.EasyEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private EasyEconomy plugin;
    private Economy economyProvider;
    public VaultHook(EasyEconomy plugin) {
        this.plugin = plugin;
        this.economyProvider = plugin.economyImplementation;
    }

    public void hook(){
        plugin.getLogger().info("Attempting to hook to Vault");
        Bukkit.getServicesManager().register(Economy.class, this.economyProvider, this.plugin, ServicePriority.Normal);
        plugin.getLogger().info("VaultAPI Successfully hooked into EasyEconomy");
    }

    public void unhook(){
        plugin.getLogger().info("Attempting to unhook from VaultAPI");
        Bukkit.getServicesManager().unregister(Economy.class, this.economyProvider);
        plugin.getLogger().info("VaultAPI Successfully unhooked from EasyEconomy");
    }

}
