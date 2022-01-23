package sk.adr3ez.darkbits;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.darkbits.listeners.playerJoin;
import sk.adr3ez.darkbits.sql.MySQL;
import sk.adr3ez.darkbits.sql.SQLGetter;
import sk.adr3ez.darkbits.utils.BitsCmd;
import sk.adr3ez.darkbits.utils.DailyCmd;
import sk.adr3ez.darkbits.utils.InvYml;

import java.sql.SQLException;

public final class DarkBits extends JavaPlugin {

    public static MySQL sql;
    public static SQLGetter sqldata;
    public static InvYml invyml;

    @Override
    public void onEnable() {

        invyml = new InvYml(this);
        sql = new MySQL();
        sqldata = new SQLGetter();

        getCommand("bits").setExecutor(new BitsCmd());
        getCommand("daily").setExecutor(new DailyCmd());
        getServer().getPluginManager().registerEvents(new playerJoin(), this);

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
