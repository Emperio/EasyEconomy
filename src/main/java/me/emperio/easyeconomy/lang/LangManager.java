package me.emperio.easyeconomy.lang;

import me.emperio.easyeconomy.EasyEconomy;
import me.emperio.easyeconomy.util.EmpUtil;

public class LangManager {
    private EasyEconomy plugin;

    public LangManager(EasyEconomy plugin) {
        this.plugin = plugin;
    }

    public String getValue(String langKey){
        return EmpUtil.getColored(plugin.getLangCfg().getString(langKey));
    }

}
