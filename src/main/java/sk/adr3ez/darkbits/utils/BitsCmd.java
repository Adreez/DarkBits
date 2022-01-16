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

        if (args.length == 0) {
            if (sender instanceof Player){
                sender.sendMessage("§7Tvoje peněženka: §b " + DarkBits.sqldata.getBits(sender.getName()));
            }
        }
        else if (args[0].equalsIgnoreCase("help")) {
            if (sender.hasPermission("bits.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lDarkBits &8| &7Help message" +
                        "\n  &b/bits set <player> <bits> &8• &7This will set some bits to player." +
                        "\n  &b/bits add <player> <bits> &8• &7This will add some bits to player." +
                        "\n  &b/bits remove <player> <bits> &8• &7This will remove some bits from player." +
                        "\n  &b/bits check <player> &8• &7This will show player wallet." +
                        "\n&7Bits can be set in decimal."));
            }else {
                sender.sendMessage(noperms);
            }
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
                            double value = Double.parseDouble(args[2]);
                            DarkBits.sqldata.setBits(targetplayer.getName(), value);
                            sender.sendMessage("Coins set!");
                        } else {
                            sender.sendMessage("Nesprávne číslo.");
                        }
                    } else {
                        sender.sendMessage("§7Hráč nebyl nalezen. &bProhledávam&7 databázi.");
                        if (DarkBits.sqldata.existsNick(args[1])) {
                            if (isDouble(args[2])) {
                                String targetplayer = args[1];
                                double value = Double.parseDouble(args[2]);
                                DarkBits.sqldata.setBits(targetplayer, value);
                                sender.sendMessage("Offline konto hráče " + targetplayer + " bylo nastaveno!");
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
                sender.sendMessage(noperms);
            }
        }

        else if (args[0].equalsIgnoreCase("add")) {
            String usage = ChatColor.translateAlternateColorCodes('&', "§8[&c&lSystém&8] &7Použití: &b/bits add <player> <bits>");

            if (sender.hasPermission("darkbits.admin")) {
                if (args.length < 3) {
                    sender.sendMessage(usage);
                }
                else if (args.length == 3) {
                    if (Bukkit.getServer().getPlayer(args[1]) != null) {
                        if (isDouble(args[2])) {
                            Player targetplayer = Bukkit.getServer().getPlayer(args[1]);
                            double value = Double.parseDouble(args[2]);
                            DarkBits.sqldata.addBits(targetplayer.getName(), value);
                            sender.sendMessage("Coins added!");
                        } else {
                            sender.sendMessage("Nesprávne číslo.");
                        }
                    } else {
                        sender.sendMessage("§7Hráč nebyl nalezen. &bProhledávam&7 databázi.");
                        if (DarkBits.sqldata.existsNick(args[1])) {
                            if (isDouble(args[2])) {
                                String targetplayer = args[1];
                                double value = Double.parseDouble(args[2]);
                                DarkBits.sqldata.addBits(targetplayer, value);
                                sender.sendMessage("Offline konto hráče " + targetplayer + " bylo přidáno!");
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
                sender.sendMessage(noperms);
            }
        }

        else if (args[0].equalsIgnoreCase("remove")) {
            String usage = ChatColor.translateAlternateColorCodes('&', "§8[&c&lSystém&8] &7Použití: &b/bits remove <player> <bits>");

            if (sender.hasPermission("darkbits.admin")) {
                if (args.length < 3) {
                    sender.sendMessage(usage);
                }
                else if (args.length == 3) {
                    if (Bukkit.getServer().getPlayer(args[1]) != null) {
                        if (isDouble(args[2])) {
                            Player targetplayer = Bukkit.getServer().getPlayer(args[1]);
                            double value = Double.parseDouble(args[2]);
                            DarkBits.sqldata.removeBits(targetplayer.getName(), value);
                            sender.sendMessage("Coins removed from players wallet!");
                        } else {
                            sender.sendMessage("Nesprávne číslo.");
                        }
                    } else {
                        sender.sendMessage("§7Hráč nebyl nalezen. &bProhledávam&7 databázi.");
                        if (DarkBits.sqldata.existsNick(args[1])) {
                            if (isDouble(args[2])) {
                                String targetplayer = args[1];
                                double value = Double.parseDouble(args[2]);
                                DarkBits.sqldata.removeBits(targetplayer, value);
                                sender.sendMessage("Offline konto hráče " + targetplayer + " bylo nalezeno!");
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
                sender.sendMessage(noperms);
            }
        }

        else if (args[0].equalsIgnoreCase("check")) {
            String usage = ChatColor.translateAlternateColorCodes('&', "§8[&c&lSystém&8] &7Použití: &b/bits check <player>");

            if (sender.hasPermission("darkbits.admin")) {
                if (args.length < 2) {
                    sender.sendMessage(usage);
                }
                else if (args.length == 2) {
                    if (DarkBits.sqldata.existsNick(args[1])) {
                        sender.sendMessage("Hráč " + args[1] + " má na účtě " + DarkBits.sqldata.getBits(args[1]));
                    } else {
                        sender.sendMessage("Konto hráče nebylo nalezeno!");
                    }
                } else {
                    sender.sendMessage("§cToo many arguments!");
                }
            } else {
                sender.sendMessage(noperms);
            }
        }

        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&lSystém&8] &7Tento příkaz neexistuje!"));
        }
        return false;
    }
}
