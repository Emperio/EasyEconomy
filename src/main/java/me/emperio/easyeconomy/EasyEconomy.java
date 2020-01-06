package me.emperio.easyeconomy;

import me.emperio.easyeconomy.commands.EconomyMasterCommand;
import me.emperio.easyeconomy.commands.player.BalanceCommand;
import me.emperio.easyeconomy.commands.player.PayCommand;
import me.emperio.easyeconomy.config.Config;
import me.emperio.easyeconomy.config.ConfigManager;
import me.emperio.easyeconomy.economy.EconomyImplementation;
import me.emperio.easyeconomy.economy.EconomyManager;
import me.emperio.easyeconomy.events.PlayerJoinsServer;
import me.emperio.easyeconomy.hooks.VaultHook;
import me.emperio.easyeconomy.lang.LangManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class EasyEconomy extends JavaPlugin {

    private ConfigManager cfgManager;
    private Config dataCfg;
    private Config langCfg;

    private LangManager lm;

    public EconomyImplementation economyImplementation;
    private EconomyManager ecoManager;

    private VaultHook vaultHook;

    @Override
    public void onEnable() {
        registerConfigs();
        lm = new LangManager(this);

        loadBalances();
        registerCommands();
        registerEvents();
        registerSettings();

        economyImplementation = new EconomyImplementation(this);

        vaultHook = new VaultHook(this);
        vaultHook.hook();
    }

    public void reload(){
        registerConfigs();
        lm = new LangManager(this);
        loadBalances();
        registerSettings();
    }

    @Override
    public void onDisable() {
        saveBalances();
        vaultHook.unhook();
    }

    private void registerCommands(){
        this.getCommand("eco").setExecutor(new EconomyMasterCommand(this));
        this.getCommand("pay").setExecutor(new PayCommand(this));
        this.getCommand("balance").setExecutor(new BalanceCommand(this));
    }

    private void loadBalances(){
        HashMap<UUID, Integer> bals = new HashMap<>();
        for(String key : getDataConfig().getConfigurationSection("balances").getKeys(false)){
            UUID uuid = UUID.fromString(key);
            int balance = getDataConfig().getInt("balances." + key);
            bals.put(uuid, balance);
        }
        ecoManager = new EconomyManager(bals, this);
    }

    private void saveBalances(){
        HashMap<UUID, Integer> bals = ecoManager.getBalancesMap();
        for(UUID each : bals.keySet()){
            getDataConfig().set("balances." + each, bals.get(each));
        }
        saveDataConfig();
    }

    private void registerConfigs(){
        cfgManager = new ConfigManager(this);
        saveDefaultConfig();

        cfgManager.registerConfig("lang.yml");
        langCfg = cfgManager.getConfig("lang.yml");

        cfgManager.registerConfig("data.yml");
        dataCfg = cfgManager.getConfig("data.yml");
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerJoinsServer(this), this);
    }

    private void registerSettings(){
        Settings.DEFAULT_BAL = getConfig().getInt("default-balance");
        Settings.CURRENCY_NAME_PLURAL = getConfig().getString("currency-name.plural");
        Settings.CURRENCY_NAME_SINGULAR = getConfig().getString("currency-name.singular");
        Settings.CURRENCY_PREFIX = getConfig().getString("currency-name.prefix");
        Settings.ALLOW_NEGATIVE_BALANCES = getConfig().getBoolean("allow-negative-balances");
        Settings.ALLOW_CONSOLE_ADMIN = getConfig().getBoolean("allow-console-admin");
        Settings.MAX_BALANCE = getConfig().getInt("max-balance");
    }

    public FileConfiguration getDataConfig(){
        return dataCfg.getConfig();
    }

    public FileConfiguration getLangCfg(){
        return langCfg.getConfig();
    }

    public void saveDataConfig(){
        dataCfg.save();
    }

    public EconomyManager getEcoManager() {
        return ecoManager;
    }

    public LangManager getLangManager(){
        return lm;
    }

    public Economy getVaultEconomy(){
        return economyImplementation;
    }
}
