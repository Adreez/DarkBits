package sk.adr3ez.darkbits.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.adr3ez.darkbits.DarkBits;

public class BitsCmd implements CommandExecutor {

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String noperms = ChatColor.translateAlternateColorCodes('&', "&8[&c&lSystém&8] &7Na toto nemáš povolení!");

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lDarkBits &8| &7Help message" +
                    "\n  &b/bits set <player> <bits> &8• &7This will set some bits to player." +
                    "\n&7Bits can be set in decimal."));
        }

        else if (args[0].equalsIgnoreCase("set")) {
            String usage = ChatColor.translateAlternateColorCodes('&', "§8[&c&lSystém&8] &7Použití: &b/bits set <player> <bits>");

            if (sender.hasPermission("darkbits.admin")) {
                if (args.length < 3) {
                    sender.sendMessage(usage);
                }
                else if (args.length == 3) {
                    if (Bukkit.getServer().getPlayer(args[1]) != null) {
                        if (isDouble(args[2])) {
                            Player targetplayer = Bukkit.getServer().getPlayer(args[1]);
                            double addcoins = Double.parseDouble(args[2]);

                            sender.sendMessage("Coins set!");
                        } else {
                            sender.sendMessage("Nesprávne číslo.");
                        }
                    } else {
                        sender.sendMessage("§7Hráč nebyl nalezen. &bProhledávam&7 databázi.");
                        if (DarkBits.sqldata.existsNick(args[1])) {
                            if (isDouble(args[2])) {
                                sender.sendMessage("Offline konto hráče xx bylo nastaveno!");
                            }else {
                                sender.sendMessage("Nesprávne číslo.");
                            }
                        } else {
                            sender.sendMessage("Hráč nebyl nalezen.");
                        }
                    }
                } else {
                    sender.sendMessage("§cToo many arguments!");
                }
            } else {
                sender.sendMessage("noperms");
            }
        }

        return false;
    }
}
