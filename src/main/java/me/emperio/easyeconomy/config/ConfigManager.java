package me.emperio.easyeconomy.config;

import me.emperio.easyeconomy.EasyEconomy;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private EasyEconomy plugin;

    //needs cleanup

    public ConfigManager(EasyEconomy plugin) {
        this.plugin = plugin;
    }

    public void registerConfig(String cfgName){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }

        String fileType = "";

        if(!cfgName.contains(".yml")) fileType = ".yml";

        File cfgFile = new File(plugin.getDataFolder(), cfgName + fileType);
        if(!cfgFile.exists()){
            plugin.getLogger().info(cfgFile.getName() + " does not exist, creating...");
            try {
                plugin.saveResource(cfgFile.getName(), false);
            }
            catch(Exception e){
                e.printStackTrace();
                plugin.getLogger().info("An error occured while creating " + cfgFile.getName());
                plugin.getLogger().info("Please refer to the above stacktrace.");

                return;
            }
            plugin.getLogger().info(cfgFile.getName() + " has been successfully created.");
        }
    }

    public Config getConfig(String cfgName){
        String fileType = "";
        if(!cfgName.contains(".yml")) fileType = ".yml";
        File cfgFile = new File(plugin.getDataFolder(), cfgName + fileType);

        if(!cfgFile.exists()){
            plugin.getLogger().info("Could not fetch " + cfgFile.getName() + ", has it been registered?");
            return null;
        }

        return (new Config(YamlConfiguration.loadConfiguration(cfgFile), cfgFile));

    }

}
