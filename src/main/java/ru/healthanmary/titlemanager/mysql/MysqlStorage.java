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
    public Title getTitleById(int titleId) {
        try (Connection connection = createConnection()) {
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM titles WHERE id = ?
            """);
            ps.setInt(1, titleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title_text = rs.getString("title");
                String player_name = rs.getString("player_name");
                Timestamp request_date = rs.getTimestamp("request_date");
                Timestamp accept_date = rs.getTimestamp("accept_date");
                String accepted_admin = rs.getString("accepted_admin");
                String admin_comment = rs.getString("admin_comment");
                Title.State state = Title.State.valueOf(rs.getString("state"));

                return new Title(titleId, title_text, player_name, request_date, accept_date, accepted_admin, admin_comment, state);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Title> getArrayOfTitles(String playerName) {
        ArrayList<Title> titles = new ArrayList<>();
        try (Connection connection = createConnection();
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `titles` WHERE LOWER(player_name) = LOWER(?)
        """)){
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title_text = rs.getString("title");
                String player_name = rs.getString("player_name");
                Timestamp request_date = rs.getTimestamp("request_date");
                Timestamp accept_date = rs.getTimestamp("accept_date");
                String accepted_admin = rs.getString("accepted_admin");
                String admin_comment = rs.getString("admin_comment");
                Title.State state = Title.State.valueOf(rs.getString("state"));

                titles.add(new Title(id, title_text, player_name, request_date, accept_date, accepted_admin, admin_comment, state));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    public Title getCurrentTitleByName(String playerName) {
        try (Connection connection = createConnection()){
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `current-titles` WHERE LOWER(player_name) = LOWER(?)
            """);
            ps.setString(1, playerName);
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
    public String getPlayerPoints(String playerName) {
        try (Connection connection = createConnection();
        PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `player-points` WHERE LOWER(player_name) = LOWER(?)
            """);){
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("points");
            } else {
                PreparedStatement pst = connection.prepareStatement("""
                INSERT INTO `player-points`(player_name, points) VALUES(?, ?)
            """);
                pst.setString(1, playerName);
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
    public void setPlayerPoints(String playerName, int points) {
        try (Connection connection = createConnection();
        PreparedStatement ps = connection.prepareStatement("""
            INSERT INTO `player-points`(player_name, points) VALUES ()
        """)){

        }
    }

    @Override
    public void resetPlayerPoints(String playerName) {

    }

    @Override
    public void takePlayerPoints(String playerName, int points) {

    }
    @Override
    public ArrayList<Title> getPendingTitles() {
        return null;
    }

    @Override
    public boolean hasTitle(String playerName, int id) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            SELECT EXISTS(SELECT 1 FROM titles WHERE player_name = ? and id = ? and state = 'ACCEPTED')
        """)){
            ps.setString(1, playerName);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getBoolean(1)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void setCurrentTitle(String playerName, Integer titleId) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `current-titles` WHERE LOWER(player_name) = LOWER(?)
            """);) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `current-titles` SET player_name = ?, id = ? WHERE LOWER(player_name) = ?
            """);
                if (titleId == null) {
                    ps2.setNull(2, Types.INTEGER);
                } else {
                    ps2.setInt(2, titleId);
                }
                ps2.setString(1, playerName);
                ps2.setString(3, playerName);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps2 = connection.prepareStatement("""
                INSERT INTO `current-titles`(player_name, id) VALUES(?, ?)
            """);
                ps2.setString(1, playerName);
                ps2.setInt(2, titleId);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
