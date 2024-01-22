package projectbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    
    static Logger logger = LogManager.getLogger(Constants.class);

    public static String chormDriver = "C:\\Users\\jayac\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    
    /*                       Azure database                                */
    
    public static final String JDBC_URL = "jdbc:sqlserver://ims2023.database.windows.net:1433;database=IMS;";
    public static final String DATABASE_NAME = "IMS";
    public static final String USER = "Jaya@ims2023";
    public static final String PASSWORD = "JcV@5818";
    
    public static String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    public static String azureConnection = JDBC_URL + "user=" + USER + ";password=" + PASSWORD + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    
    public static Connection connectAzure() {
        Connection conn = null;
        try {
            // Load the JDBC driver
            Class.forName(className);

            // Establish the connection
            conn = DriverManager.getConnection(azureConnection);
           // System.out.println("Connected to the database");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }

     public static boolean tableExists(Connection connection, String tableName) {
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT 1 FROM " + tableName + " WHERE 1 = 0";
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            logger.error("An error occurred while checking existene of table.", e);
            return false;
        }
    }
     
    /*                      DataBases Path                                */

    public static String ProductDataBase = "C:\\IMS3\\DB\\pro1.db";

    public static String employeeDataBase = "C:\\IMS3\\DB\\emp1.db";

    public static String mangeDataBase = "C:\\IMS3\\DB\\mange1.db";

    public static String splashScreen = "C:\\IMS3\\splash.gif";

    public static String iconPath = "C:\\IMS3\\Icon.png";

    // Bill path is hard coded in last file itself 
    /*                      Fonts Path                               */
    public static String calibarRegular = "C:\\IMS3\\fonts\\CalibriRegular.ttf";

    public static String calibarBold = "C:\\IMS3\\fonts\\CalibriBold.ttf";

    /*                      Product                                */
    public static String productDB = "jdbc:sqlite:C:\\IMS3\\DB\\pro1.db";

    public static String ProductTable = "purchased";

    public static String sellTable = "ProductSellTable";

    public static String bill = "bill";

    public static String purchaseTable = "Purchase";

    public static String salesTable = "salesTable";

    public static String totalAmount = "total";

    /*                      Employee                                */
    public static String employeeDB = "jdbc:sqlite:C:\\IMS3\\DB\\emp1.db";

    public static String employeeTable = "employeeData";

    public static String employeeAttendanceTable = "employeeAttendance";

    /*                      Manage                                */
    public static String mangeDB = "jdbc:sqlite:C:\\IMS3\\DB\\mange1.db";

    public static String csDue = "csDue";

    public static String vendourDue = "vendourDue";

    public static String empDue = "empDue";

    public static String manage = "manage";

    public static String expensesTable = "expensesTable";

    public static String vendourTable = "vendourTable";

    public static String customerTable = "customerTableDataFinal";

    public static String BillHeaders = "billHeaders";

    /*                      Bill                                */
    public static String Title = new String("Inventory Managment System ");
    public static String subTitle = new String("Invoice");
}
