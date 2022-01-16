package sk.adr3ez.darkbits.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import sk.adr3ez.darkbits.DarkBits;

public class playerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (DarkBits.sql.isConnected())  DarkBits.sqldata.createPlayer(e.getPlayer());
    }
}
