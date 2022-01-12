package sk.adr3ez.darkbits;

import org.bukkit.plugin.java.JavaPlugin;
import sk.adr3ez.darkbits.utils.sql.MySQL;
import sk.adr3ez.darkbits.utils.sql.SQLGetter;

import java.sql.SQLException;

public final class DarkBits extends JavaPlugin {

    public static MySQL sql;
    public static SQLGetter sqldata;

    @Override
    public void onEnable() {
        // Plugin startup logic
        sql = new MySQL();
        sqldata = new SQLGetter();

        try {
            sql.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
