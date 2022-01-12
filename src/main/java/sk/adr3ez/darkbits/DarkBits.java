package sk.adr3ez.darkbits;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.darkbits.sql.MySQL;
import sk.adr3ez.darkbits.sql.SQLGetter;
import sk.adr3ez.darkbits.utils.BitsCmd;

import java.sql.SQLException;

public final class DarkBits extends JavaPlugin {

    public static MySQL sql;
    public static SQLGetter sqldata;

    @Override
    public void onEnable() {

        sql = new MySQL();
        sqldata = new SQLGetter();

        this.getCommand("bits").setExecutor(new BitsCmd());
        try {
            sql.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§bDarkBits §cDatabase has not been connected!");
        }

        if (sql.isConnected()) {
            Bukkit.getServer().getConsoleSender().sendMessage("§bDarkBits §aSQL has been connected.");
            sqldata.createTable();
            Bukkit.getServer().getConsoleSender().sendMessage("§bDarkBits §aTable has been created.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (sql.isConnected()) {
            sql.disconnect();
        }
    }
}
