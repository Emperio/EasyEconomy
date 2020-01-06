package me.emperio.easyeconomy.config;

import me.emperio.easyeconomy.util.EmpUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private FileConfiguration cfg;
    private File cfgFile;

    public Config(FileConfiguration cfg, File cfgFile) {
        this.cfg = cfg;
        this.cfgFile = cfgFile;
    }

    public boolean save(){
        try {
            cfg.save(cfgFile);
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void reload(){
        cfg = YamlConfiguration.loadConfiguration(cfgFile);
    }

    public void logMessage(String message){
        EmpUtil.logMessage(message);
    }

    public FileConfiguration getConfig() {
        return cfg;
    }

    public File getConfigFile() {
        return cfgFile;
    }

}
