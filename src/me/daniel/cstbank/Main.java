package me.daniel.cstbank;

import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class Main extends JavaPlugin {
public static Economy econ = null;
public static Main plugin;
    @Override
    public void onEnable() {
        plugin = this;
        setupEconomy();
        getCommand("banco").setExecutor(new banco());
    }
    @Override
    public void onDisable() {
        
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    

}
