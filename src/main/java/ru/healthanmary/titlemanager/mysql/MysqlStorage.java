package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.config.Config;
import ru.healthanmary.titlemanager.util.Title;

import java.sql.*;
import java.util.ArrayList;

public class MysqlStorage implements Storage {
    private String url = "jdbc:mysql://" + Config.MySqlData.HOST + ":" + Config.MySqlData.PORT + "/" + Config.MySqlData.DB_NAME;
    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, Config.MySqlData.USERNAME, Config.MySqlData.PASSWORD);
    }

    @Override
    public Title getTitleById(int id) {
        try (Connection connection = createConnection()) {
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM titles WHERE id = ?
            """);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title_text = rs.getString("title");
                String player_name = rs.getString("player_name");
                Timestamp request_date = rs.getTimestamp("request_date");
                Timestamp accept_date = rs.getTimestamp("accept_date");
                String accepted_admin = rs.getString("accepted_admin");
                String admin_comment = rs.getString("admin_comment");
                boolean is_accepted = rs.getBoolean("is_accepted");

                return new Title(id, title_text, player_name, request_date, accept_date, accepted_admin, admin_comment, is_accepted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Title> arrayOfTitles(int id) {
        return null;
    }

    @Override
    public Title getCurrentTitleByName(String name) {
        try (Connection connection = createConnection()){
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `current-titles` WHERE LOWER(player_name) = LOWER(?)
            """);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getTitleById(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPlayerPoints(String name) {
        try (Connection connection = createConnection();
        PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `player-points` WHERE LOWER(player_name) = LOWER(?)
            """);){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("points");
            } else {
                PreparedStatement pst = connection.prepareStatement("""
                INSERT INTO `player-points`(player_name, points) VALUES(?, ?)
            """);
                pst.setString(1, name);
                pst.setInt(2, 0);
                pst.executeUpdate();
                return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Неизвестно";
    }

    @Override
    public void setPlayerPoints(String name, int points) {

    }

    @Override
    public void resetPlayerPoints(String name) {

    }

    @Override
    public void takePlayerPoints(String name, int points) {

    }
    @Override
    public ArrayList<Title> getPendingTitles() {
        return null;
    }

    @Override
    public void setCurrentTitle(String name, Integer id) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `current-titles` WHERE LOWER(player_name) = LOWER(?)
            """);) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `current-titles` SET player_name = ?, id = ? WHERE LOWER(player_name) = ?
            """);
                ps2.setString(1, name);
                ps2.setInt(2, id);
                ps2.setString(3, name);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps2 = connection.prepareStatement("""
                INSERT INTO `current-titles`(player_name, id) VALUES(?, ?)
            """);
                ps2.setString(1, name);
                ps2.setInt(2, id);
                ps2.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
