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

        String noperms = ChatColor.translateAlternateColorCodes('&', "&c✘ &8| &c&lChyba! &7Pro vykonáni tohoto příkazu nemáš dostatečné oprávnění!");

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
                        "\n  &b/bits send <player> <bits> &8• &7This will transfer some bits to player's account." +
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
                        if (DarkBits.sqldata.exists(args[1])) {
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
                        if (DarkBits.sqldata.exists(args[1])) {
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
                        if (DarkBits.sqldata.exists(args[1])) {
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
                    if (DarkBits.sqldata.exists(args[1])) {
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

        else if (args[0].equalsIgnoreCase("send")) {
            String usage = ChatColor.translateAlternateColorCodes('&', "§8[&c&lSystém&8] &7Použití: &b/bits send <player> <bits>");

            if (sender.hasPermission("darkbits.send")) {
                if (sender instanceof Player) {
                    if (args.length < 3) {
                        sender.sendMessage(usage);
                    }
                    else if (args.length == 3) {
                        if (Bukkit.getServer().getPlayer(args[1]) != null) {
                            if (isDouble(args[2])) {
                                Player targetplayer = Bukkit.getServer().getPlayer(args[1]);
                                double value = Double.parseDouble(args[2]);

                                if (DarkBits.sqldata.canReceive(targetplayer.getName())) {
                                    if (!sender.getName().equals(targetplayer.getName())) {
                                        if (value >= 5) {
                                            if (DarkBits.sqldata.getBits(sender.getName()) >= value) {
                                                DarkBits.sqldata.removeBits(sender.getName(), value);
                                                DarkBits.sqldata.addBits(targetplayer.getName(), value);

                                                sender.sendMessage("Bity byly provedeny na hráčuv účet! " + value);
                                                targetplayer.sendMessage("Obdrželi jste " + value + " bitú od " + sender.getName());
                                            } else {
                                                sender.sendMessage("Nedostatek bitu na účtě!");
                                            }
                                        } else {
                                            sender.sendMessage("Minimálni hodnota pro zasláni bitú je: 5");
                                        }
                                    } else {
                                        sender.sendMessage("Nemužeš poslat bity sám sobě!");
                                    }
                                } else {
                                    sender.sendMessage("Zadaný hráč nemá zakoupený SEND-PASS.");
                                }

                            } else {
                                sender.sendMessage("Nesprávne číslo.");
                            }
                        } else {
                            sender.sendMessage("§7Hráč nebyl nalezen. &bProhledávam&7 databázi.");
                            if (DarkBits.sqldata.exists(args[1])) {
                                if (isDouble(args[2])) {
                                    String targetplayer = args[1];
                                    double value = Double.parseDouble(args[2]);

                                    if (DarkBits.sqldata.canReceive(targetplayer)) {
                                        if (DarkBits.sqldata.getBits(sender.getName()) >= value) {
                                            DarkBits.sqldata.removeBits(sender.getName(), value);
                                            DarkBits.sqldata.addBits(targetplayer, value);

                                            sender.sendMessage("Bity byly provedeny na hráčuv účet! " + value);
                                        } else {
                                            sender.sendMessage("Nedostatek bitu na účtě!");
                                        }
                                    } else {
                                        sender.sendMessage("Zadaný hráč nemá zakoupený SEND-PASS.");
                                    }

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
                    sender.sendMessage("Console isn't allowed to use this command!");
                }
            } else {
                sender.sendMessage(noperms + ChatColor.translateAlternateColorCodes('&', "\n§&6➹ &8| &6&lTIP &8• &7V případe že máte zájem přijímat, odesílat a využívat bitový generátor musíte si zakupit &bSEND-PASS &7na &bstore.darkanian.eu"));
            }
        }

        else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c✘ &8| &c&l&oChyba! &7Příkaz neexistuje!"));
        }
        return false;
    }
}
