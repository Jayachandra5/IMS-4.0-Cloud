package projectbms;

import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class manage {

    public static void createNewDatabase() {

        try (Connection conn = Constants.connectAzure()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();

                Logger logger = LogManager.getLogger(manage.class);
                logger.info("The driver name is {}", meta.getDriverName());
                logger.info("A new database has been created.");
            }

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while creating the database.", e);
        }
    }

    static Logger logger = LogManager.getLogger(manage.class);

    public static void createNewTable() {

        try (Connection connection = Constants.connectAzure()) {
            if (!Constants.tableExists(connection, Constants.manage)) {
                String sql = "CREATE TABLE " + Constants.manage + " (\n"
                        + "	id INT PRIMARY KEY IDENTITY,\n"
                        + "	name NVARCHAR(50) NOT NULL,\n"
                        + "	amount real \n"
                        + ");";

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                    logger.info("Table created successfully.");
                } catch (SQLException e) {
                    logger.error("Error creating table: " + e.getMessage());
                }
            } else {
                logger.info("Table already exists.");
            }
        } catch (SQLException e) {
            logger.error("Database connection error: " + e.getMessage());
        }
    }

    public void insert(String name, double amount) {
        String sql = "INSERT INTO " + Constants.manage + "(name,amount) VALUES(?,?)";

        try (Connection conn = Constants.connectAzure(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, amount);

            pstmt.executeUpdate();
            System.out.println("data Inserted.");

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while inserting into manage .", e);
        }
    }

    public void increaseAmount(String name, double amount) {
        String sql = "SELECT  name, amount "
                + "FROM " + Constants.manage + " WHERE name = ?";

        String sql2 = "UPDATE " + Constants.manage + " SET amount = ?  "
                + "WHERE name = ?";

        try (Connection conn = Constants.connectAzure(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            double price = 0;
            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            pstmt1.setDouble(1, amount = price + amount);
            pstmt1.setString(2, name);

            pstmt1.executeUpdate();

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while incressing amount in mange.", e);
        }
    }

    public void decreaseAmount(String name, double amount) {
        String sql = "SELECT  name, amount "
                + "FROM " + Constants.manage + " WHERE name = ?";

        String sql2 = "UPDATE " + Constants.manage + " SET amount = ?  "
                + "WHERE name = ?";

        try (Connection conn = Constants.connectAzure(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;
            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            pstmt1.setDouble(1, amount = price - amount);
            pstmt1.setString(2, name);

            pstmt1.executeUpdate();

        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while decersing amount in mange.", e);
        }
    }

    public void display(String name) {

        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        try (Connection conn = Constants.connectAzure(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            double price = 0;

            while (rs.next()) {
                price = rs.getDouble("amount");
            }

            System.out.println(price);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while displaying amount in mange.", e);
        }
    }

    public void revenue() {
        String name1 = "profit";
        String name2 = "expenses";

        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        String sql1 = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";
        try (Connection conn = Constants.connectAzure(); PreparedStatement pstmt = conn.prepareStatement(sql); PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt.setString(1, name1);
            pstmt1.setString(1, name2);

            ResultSet rs = pstmt.executeQuery();
            ResultSet rs1 = pstmt1.executeQuery();

            double profit = 0;

            while (rs.next()) {
                profit = rs.getDouble("amount");
            }

            double expenses = 0;
            while (rs1.next()) {
                expenses = rs1.getDouble("amount");
            }

            double revenue = profit - expenses;

            System.out.println(revenue);
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while displaying revenue amount in mange.", e);
        }
    }

    public double getvalue(String name) {
        String sql = "SELECT  * "
                + "FROM " + Constants.manage + " WHERE name = ?";

        double price = 0;

        try (Connection conn = Constants.connectAzure(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                price = rs.getDouble("amount");
            }
        } catch (SQLException e) {
            Logger logger = LogManager.getLogger(manage.class);
            logger.error("An error occurred while getting amount in mange.", e);
        }
        return price;
    }
}
