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
    public Title getTitleByName(String name) {
        try (Connection connection = createConnection()){
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `current-titles` WHERE LOWER(player_name) = LOWER(?)
            """);
            ps.setString(1, name);
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                int titleId = rs.getInt("id");
                System.out.println(titleId);
                return getTitleById(titleId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getPlayerPoints(String name) {
        return 0;
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
}
