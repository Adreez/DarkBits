package sk.adr3ez.darkbits.utils.sql;

import org.bukkit.entity.Player;
import sk.adr3ez.darkbits.DarkBits;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {


    String table = "data";

    /*
     * NAME UUID Credits Silvers
     */
    public void createTable() {
        PreparedStatement ps;
        try {
            ps = DarkBits.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (NAME VARCHAR(100),UUID VARCHAR(100),POINTS INT(100),PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)) {
                PreparedStatement ps2 = DarkBits.sql.getConnection().prepareStatement("INSERT IGNORE INTO " + table + " (NAME,UUID,POINTS) VALUES (?,?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setString(3, "");
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return false;
    }


    public void addPoints(UUID uuid, int points) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET POINTS=? WHERE UUID=?");
            ps.setInt(1, (getPoints(uuid) + points));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePoints(UUID uuid, int points) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("UPDATE " + table + " SET POINTS=? WHERE UUID=?");
            ps.setInt(1, (getPoints(uuid) - points));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPoints(UUID uuid) {
        try {
            PreparedStatement ps = DarkBits.sql.getConnection().prepareStatement("SELECT POINTS FROM " + table + " WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points = 0;

            if (rs.next()) {
                points = rs.getInt("POINTS");
                return points;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
