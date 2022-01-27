package sk.adr3ez.darkbits.sql;

import org.bukkit.entity.Player;
import sk.adr3ez.darkbits.DarkBits;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {


    String table = "data";

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = DarkBits.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (NICK VARCHAR(100), UUID VARCHAR(100), BITS DOUBLE, CANRECEIVE VARCHAR(5), TIMEMILLIS BIGINT(100), PRIMARY KEY (NICK))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(player.getName())) {
                PreparedStatement ps2 = DarkBits.sql.getConnection().prepareStatement("INSERT IGNORE INTO " + table + " (NICK,UUID,BITS,CANRECEIVE,TIMEMILLIS) VALUES (?,?,?,?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setDouble(3, 0);
                ps2.setString(4, "false");
                ps2.setLong(5, 0);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String nick) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE NICK=?");
            ps.setString(1, nick);
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setBits(String nick, double bits) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET BITS=? WHERE NICK=?");
            ps.setDouble(1, bits);
            ps.setString(2, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean canReceive(String nick) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("SELECT CANRECEIVE FROM " + table + " WHERE NICK=?");
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            String canreceive;
            if (rs.next()) {
                canreceive = rs.getString("CANRECEIVE");
                if (canreceive.equals("false"))
                    return false;
                if (canreceive.equals("true"))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*public void setCanReceive(String nick, boolean canreceive) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET CANRECEIVE=? WHERE NICK=?");
            ps.setBoolean(1, canreceive);
            ps.setString(2, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void addBits(String nick, double bits) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET BITS=? WHERE NICK=?");
            ps.setDouble(1, getBits(nick) + bits);
            ps.setString(2, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBits(String nick, double bits) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET BITS=? WHERE NICK=?");
            ps.setDouble(1, (getBits(nick) - bits));
            ps.setString(2, nick);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBits(String nick) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("SELECT BITS FROM " + table + " WHERE NICK=?");
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            double bits;
            if (rs.next()) {
                bits = rs.getDouble("BITS");
                return bits;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public long getTimeMillis(String nick) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("SELECT TIMEMILLIS FROM " + table + " WHERE NICK=?");
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            long bits;
            if (rs.next()) {
                bits = rs.getLong("TIMEMILLIS");
                return bits;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void setTimeMillis(String nick, long millis) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET TIMEMILLIS=? WHERE NICK=?");
            ps.setLong(1, millis);
            ps.setString(2, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
