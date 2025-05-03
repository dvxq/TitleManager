package ru.healthanmary.titlemanager.mysql;

import ru.healthanmary.titlemanager.config.MysqlConfigParser;
import ru.healthanmary.titlemanager.util.Title;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlStorage implements Storage {
    private final MysqlConfigParser config;
    private final String url;

    public MysqlStorage(MysqlConfigParser config) {
        this.config = config;
        url = "jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabaseName();
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, config.getUsername(), config.getPassword());
    }

    @Override
    public Title getTitleById(int titleId) {
        try (Connection connection = createConnection()) {
            PreparedStatement ps = connection.prepareStatement("""
                    SELECT * FROM titles WHERE id = ?
            """);
            ps.setInt(1, titleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return buildTitle(rs);

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
        """)) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                titles.add(buildTitle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    public Title getCurrentTitleByName(String playerName) {
        try (Connection connection = createConnection()) {
            PreparedStatement ps = connection.prepareStatement("""
                SELECT * FROM `titles` AS t
                JOIN `current-titles` AS ct ON t.id = ct.fk_title_id
                WHERE t.player_name = ?;
            """);
            ps.setString(1, playerName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return buildTitle(rs);
            else return null;
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
            """)) {
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
    public void setPlayerPoints(String playerName, Integer points) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            SELECT * FROM `player-points` WHERE LOWER(player_name) = ?
        """)) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `player-points` SET points = ? WHERE LOWER(player_name) = ?
            """);
                ps2.setInt(1, points);
                ps2.setString(2, playerName);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps2 = connection.prepareStatement("""
                INSERT INTO `player-points`(player_name, points) VALUES(?, ?)
            """);
                ps2.setString(1, playerName);
                ps2.setInt(2, points);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetPlayerPoints(String playerName) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            SELECT * FROM `player-points` WHERE LOWER(player_name) = ?
        """)) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `player-points` SET points = 0 WHERE LOWER(player_name) = ?
            """);
                ps2.setString(1, playerName);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps2 = connection.prepareStatement("""
                INSERT INTO `player-points`(player_name, points) VALUES(?, 0)
            """);
                ps2.setString(1, playerName);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void takePlayerPoints(String playerName, int points) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            SELECT * FROM `player-points` WHERE LOWER(player_name) = ?
        """)) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer playerPoints = rs.getInt(2);
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `player-points` SET points = ? WHERE LOWER(player_name) = ?
            """);
                ps2.setInt(1, playerPoints - points);
                ps2.setString(2, playerName);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps2 = connection.prepareStatement("""
                INSERT INTO `player-points`(player_name, points) VALUES(?, ?)
            """);
                ps2.setString(1, playerName);
                ps2.setInt(2, 0 - points);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void givePlayerPoints(String playerName, int points) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            SELECT * FROM `player-points` WHERE LOWER(player_name) = ?
        """)) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer playerPoints = rs.getInt(2);
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `player-points` SET points = ? WHERE LOWER(player_name) = ?
            """);
                ps2.setInt(1, playerPoints + points);
                ps2.setString(2, playerName);
                ps2.executeUpdate();
            } else {
                PreparedStatement ps2 = connection.prepareStatement("""
                INSERT INTO `player-points`(player_name, points) VALUES(?, ?)
            """);
                ps2.setString(1, playerName);
                ps2.setInt(2, 0 + points);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasTitle(String playerName, int id) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            SELECT EXISTS(SELECT 1 FROM titles WHERE player_name = ? and id = ? and state = 'ACCEPTED')
        """)) {
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
            """)) {
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("""
                UPDATE `current-titles` SET player_name = ?, fk_title_id = ? WHERE LOWER(player_name) = ?
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
                INSERT INTO `current-titles`(player_name, fk_title_id) VALUES(?, ?)
            """);
                ps2.setString(1, playerName);
                ps2.setInt(2, titleId);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTitleToReview(String playerName, String title) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            INSERT INTO `titles`(title, player_name, request_date, state) VALUES (?, ?, ?, ?)
        """)) {
            ps.setString(1, title.replace("&", "§"));
            ps.setString(2, playerName);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(4, "UNDER_REVIEW");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTitleState(Title.State state, int titleId) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
             UPDATE `titles` SET state = ? WHERE id = ?
         """)) {
            ps.setString(1, state.name());
            ps.setInt(2, titleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Title> getReviewTitles() {
        List<Title> reviewTitles = new ArrayList<>();
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
             SELECT * FROM `titles` WHERE state = 'UNDER_REVIEW'
         """)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviewTitles.add(buildTitle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewTitles;
    }

    @Override
    public Title.State getTitleState(int titleId) {
        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
             SELECT state FROM titles WHERE id = ?
         """)) {
            ps.setInt(1, titleId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String stateStr = rs.getString("state");
                return Title.State.valueOf(stateStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void reviewTitle(int titleId, String adminName, String adminComment, boolean isAccepted) {
        String newState = isAccepted ? Title.State.ACCEPTED.name() : Title.State.REJECTED.name();

        try (Connection connection = createConnection();
             PreparedStatement ps = connection.prepareStatement("""
            UPDATE `titles`
            SET state = ?, review_admin = ?, admin_comment = ?, review_date = NOW()
            WHERE id = ?
        """)) {

            ps.setString(1, newState);
            ps.setString(2, adminName);
            ps.setString(3, adminComment);
            ps.setInt(4, titleId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Title buildTitle(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titleText = rs.getString("title");
        String playerNameDb = rs.getString("player_name");
        Timestamp requestDate = rs.getTimestamp("request_date");
        Timestamp reviewDate = rs.getTimestamp("review_date");
        String reviewAdmin = rs.getString("review_admin");
        String adminComment = rs.getString("admin_comment");
        Title.State state = Title.State.valueOf(rs.getString("state"));
        return new Title(id, titleText, playerNameDb, requestDate, reviewDate, reviewAdmin, adminComment, state);
    }
}
