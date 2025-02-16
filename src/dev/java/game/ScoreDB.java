package game;

import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.swing.*;
import java.sql.*;

public class ScoreDB {

    public static Connection Setup() throws Exception {
        try {
            EmbeddedDataSource ds = new EmbeddedDataSource();
            ds.setDatabaseName("foobar");
            ds.setCreateDatabase("create");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            System.out.println(conn);

            try {
                stmt.executeUpdate("DROP TABLE highScores");
            } catch (Exception var6) {
                System.out.println("Could not drop table highScores, highScores table does not exist.");
            }

            try {
                stmt.executeUpdate("DROP TABLE time");
            } catch (Exception var5) {
                System.out.println("Could not drop table time, time table does not exist.");
            }

            stmt.executeUpdate("CREATE TABLE highScores (id INTEGER PRIMARY KEY, score integer, name VARCHAR(100))");
            stmt.executeUpdate("CREATE TABLE time (id INTEGER PRIMARY KEY, moves integer, name VARCHAR(255))");
            stmt.executeUpdate("INSERT INTO highScores VALUES (1, 100, 'Joe')");
            stmt.executeUpdate("INSERT INTO highScores VALUES (2, 200, 'Amy')");
            stmt.executeUpdate("INSERT INTO time VALUES (1, 100, 'Joe')");
            stmt.executeUpdate("INSERT INTO time VALUES (2, 20, 'Amy')");
            //stmt.executeUpdate("INSERT INTO animal VALUES (3, 1, 'Ester', '2002-09-09 10:36:00')");
            //stmt.executeUpdate("INSERT INTO animal VALUES (4, 1, 'Eddie', '2010-06-08 01:24:00')");
            //stmt.executeUpdate("INSERT INTO animal VALUES (5, 2, 'Zoe', '2005-11-12 03:44:00')");
            ResultSet rs = stmt.executeQuery("select id, \t\t\t\t\t\t\tscore, \t\t\t\t\t\t\t\t  name \t\t\t\t\t\t\tfrom highScores");
            safeDelete(conn, "any' or 1 = 1 or name='any");

            //while(rs.next()) {
            //    System.out.println("Person Name: " + rs.getString("name"));
            //    System.out.println(rs.getInt(1));
            //}

            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
        } catch (Exception var8) {
        }
        return null;
    }

    private static void scaryDelete(Connection conn, String name) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "delete from highScores where name = '" + name + "'";
        System.out.println(sql);
        stmt.executeUpdate(sql);
    }

    private static void safeDelete(Connection conn, String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from highScores where name = ?");
        ps.setString(1, name);
        System.out.println(ps.toString());
        ps.execute();
    }

    public void Test(Connection conn) throws SQLException {
        // input
        Statement stmt = conn.createStatement();
        String sql = "select * from highScores";
        ResultSet rs = stmt.executeQuery(sql);

        // output
        while(rs.next()) {
            System.out.println(ConsoleColors.YELLOW+"Player: " +ConsoleColors.RESET+ rs.getString("name"));
            System.out.println(ConsoleColors.YELLOW+"High Score: " +ConsoleColors.RESET+ rs.getInt(1));
        }
    }

}
