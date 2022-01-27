package sk.adr3ez.darkbits.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sk.adr3ez.darkbits.DarkBits;

import java.util.Calendar;

public class DailyCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {

            Calendar midnight = Calendar.getInstance();
            midnight.add(Calendar.DAY_OF_MONTH, 1);
            midnight.set(Calendar.HOUR_OF_DAY, 0);
            midnight.set(Calendar.MINUTE, 0);
            midnight.set(Calendar.SECOND, 0);
            midnight.set(Calendar.MILLISECOND, 0);
            long timeToMidnight = (midnight.getTimeInMillis() - System.currentTimeMillis());

            if (System.currentTimeMillis() >= DarkBits.sqldata.getTimeMillis(sender.getName()) || DarkBits.sqldata.getTimeMillis(sender.getName()) == 0) {
                DarkBits.sqldata.setTimeMillis(sender.getName(), midnight.getTimeInMillis());
                sender.sendMessage(" timer has been reseted!");
            } else {
                sender.sendMessage("You must wait " + timeToMidnight/1000/60 + " minutes or " + timeToMidnight/1000 + " seconds");
            }
        } else {
            if (args[0].equalsIgnoreCase("reset")) {
                DarkBits.sqldata.setTimeMillis(sender.getName(), 0);
            }else if (args[0].equalsIgnoreCase("open")) {
                if (args.length == 2) {
                    if (DarkBits.invyml.get().getString("Inventories." + args[1]) != null) {

                    }else {
                        sender.sendMessage("Inventar neexistuje!");
                    }
                }else {
                    sender.sendMessage("Usage");
                }
            }else {
                sender.sendMessage("Command doesnt exist!");
            }
        }

        return false;
    }
}
