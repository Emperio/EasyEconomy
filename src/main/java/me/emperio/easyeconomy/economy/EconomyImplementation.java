package me.emperio.easyeconomy.economy;

import me.emperio.easyeconomy.EasyEconomy;
import me.emperio.easyeconomy.Settings;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("Duplicates")
public class EconomyImplementation implements Economy {

    private EasyEconomy plugin;
    private EconomyManager em;

    public EconomyImplementation(EasyEconomy plugin) {
        this.plugin = plugin;
        em = plugin.getEcoManager();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "EasyEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        int amountToFormat = (int) amount;

        return NumberFormat.getNumberInstance(Locale.US).format(amountToFormat);
    }

    @Override
    public String currencyNamePlural() {
        return Settings.CURRENCY_NAME_PLURAL;
    }

    @Override
    public String currencyNameSingular() {
        return Settings.CURRENCY_NAME_PLURAL;
    }

    @Override
    public boolean hasAccount(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if(em.hasAccount(player)){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        if(em.hasAccount(player.getUniqueId())){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        return em.hasAccount(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return em.hasAccount(player.getUniqueId());
    }

    @Override
    public double getBalance(String playerName) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        return em.getBalance(player.getUniqueId());
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return em.getBalance(player.getUniqueId());
    }

    @Override
    public double getBalance(String playerName, String world) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        return em.getBalance(player.getUniqueId());
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return em.getBalance(player.getUniqueId());
    }

    @Override
    public boolean has(String playerName, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        int bal = em.getBalance(player.getUniqueId());
        if(bal >= amount) return true;
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        int bal = em.getBalance(player.getUniqueId());
        if(bal >= amount) return true;
        return false;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        int bal = em.getBalance(player.getUniqueId());
        if(bal >= amount) return true;
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        int bal = em.getBalance(player.getUniqueId());
        if(bal >= amount) return true;
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        int balance = em.getBalance(player.getUniqueId());
        int amountToWithdraw = (int) amount;

        if(amountToWithdraw > balance){
            if(!Settings.ALLOW_NEGATIVE_BALANCES){
                return new EconomyResponse(balance, balance,
                        EconomyResponse.ResponseType.FAILURE, null);
            }
        }

        em.withdraw(player.getUniqueId(), amountToWithdraw);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        int balance = em.getBalance(player.getUniqueId());
        int amountToWithdraw = (int) amount;

        if(amountToWithdraw > balance){
            if(!Settings.ALLOW_NEGATIVE_BALANCES){
                return new EconomyResponse(balance, balance,
                        EconomyResponse.ResponseType.FAILURE, null);
            }
        }

        em.withdraw(player.getUniqueId(), amountToWithdraw);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        int balance = em.getBalance(player.getUniqueId());
        int amountToWithdraw = (int) amount;

        if(amountToWithdraw > balance){
            if(!Settings.ALLOW_NEGATIVE_BALANCES){
                return new EconomyResponse(balance, balance,
                        EconomyResponse.ResponseType.FAILURE, null);
            }
        }

        em.withdraw(player.getUniqueId(), amountToWithdraw);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        int balance = em.getBalance(player.getUniqueId());
        int amountToWithdraw = (int) amount;
        em.withdraw(player.getUniqueId(), amountToWithdraw);

        if(amountToWithdraw > balance){
            if(!Settings.ALLOW_NEGATIVE_BALANCES){
                return new EconomyResponse(balance, balance,
                        EconomyResponse.ResponseType.FAILURE, null);
            }
        }

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        int balance = em.getBalance(player.getUniqueId());
        int amountToDeposit = (int) amount;
        em.deposit(player.getUniqueId(), amountToDeposit);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        int balance = em.getBalance(player.getUniqueId());
        int amountToDeposit = (int) amount;
        em.deposit(player.getUniqueId(), amountToDeposit);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        int balance = em.getBalance(player.getUniqueId());
        int amountToDeposit = (int) amount;
        em.deposit(player.getUniqueId(), amountToDeposit);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        int balance = em.getBalance(player.getUniqueId());
        int amountToDeposit = (int) amount;
        em.deposit(player.getUniqueId(), amountToDeposit);

        return new EconomyResponse(balance, em.getBalance(player.getUniqueId()),
                EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        OfflinePlayer player = Bukkit.getPlayer(playerName);
        if(!em.hasAccount(player.getUniqueId())){
            em.createAccount(player.getUniqueId(), Settings.DEFAULT_BAL);
            return true;
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if(!em.hasAccount(player.getUniqueId())){
            em.createAccount(player.getUniqueId(), Settings.DEFAULT_BAL);
            return true;
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        OfflinePlayer player = Bukkit.getPlayer(playerName);
        if(!em.hasAccount(player.getUniqueId())){
            em.createAccount(player.getUniqueId(), Settings.DEFAULT_BAL);
            return true;
        }
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        if(!em.hasAccount(player.getUniqueId())){
            em.createAccount(player.getUniqueId(), Settings.DEFAULT_BAL);
            return true;
        }
        return false;
    }
}
