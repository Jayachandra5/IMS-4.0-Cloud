package projectbms;

import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class empAttandnce {
    
    org.apache.logging.log4j.Logger logger = LogManager.getLogger(empAttandnce.class);
    
    public void createNewTable() {
        
        try (Connection connection = Constants.connectAzure()) {
            if (!Constants.tableExists(connection, Constants.employeeAttendanceTable)) {
              // SQL statement for creating a new table with a timestamp column
       String sql = "CREATE TABLE "+Constants.employeeAttendanceTable+" (\n"
                + "	id INT PRIMARY KEY IDENTITY,\n"
                + "	empname NVARCHAR(50) NOT NULL,\n"
                + "	wd real \n"
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
    
     public int markattend()
    {
         String sql = "SELECT empname,wd FROM "+Constants.employeeAttendanceTable+" ORDER BY empname";
         String sql1 ="UPDATE "+Constants.employeeAttendanceTable+" SET wd = ?  "
                + "WHERE empname = ?";
         
         Scanner sc = new Scanner(System.in);
         System.out.print("\n Enter p for Present , a for Attandnce , hp for HalfDayPresent ");
       
        try (Connection conn = Constants.connectAzure();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);
             PreparedStatement pstmt1  = conn.prepareStatement(sql1)){
            
            while (rs.next()) {
            String empname =    rs.getString("empname");
            double wd      =    rs.getDouble("wd");
            
            System.out.println(empname+" :");
            
            String attandence = sc.nextLine();
            String attendence1 = attandence.toLowerCase();
            
            if(empname == null){
                return 0;
            }
            if(empname == null){
                break;
            }
            else if("p".equals(attendence1)){
                wd=wd+1;
                
            pstmt1.setDouble(1,wd );
            pstmt1.setString(2, empname);
            pstmt1.executeUpdate();
             return 1;
            }
            
            else if ("hp".equals(attendence1)){
                wd=wd+0.5;
                
            pstmt1.setDouble(1,wd );
            pstmt1.setString(2, empname);
            pstmt1.executeUpdate();
             return 2;
            }
            else if("a".equals(attendence1)){
                System.out.println("Marked absent");
                 return 3;
            }
            else{
                System.out.println("Input MissMatch ");
                
            }
            }
        } catch (SQLException e) {
             logger.error("An error occurred while entering employee attannce ", e);
        }
         return 4;
    }
    
     public void display(){
        String sql = "SELECT id,empname,wd FROM "+Constants.employeeAttendanceTable+" ORDER BY empname";
       
        try (Connection conn = Constants.connectAzure();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            System.out.println("EMPLOYEENAME         WORKINGDAYS");
            
            while (rs.next()) {
            String empname =    rs.getString("empname");
            double wd  =    rs.getDouble("wd");
            
            System.out.printf("%-20s",empname);
            System.out.printf("     %.2f",wd);
            System.out.println("\n\n");
                                   
            }
        } catch (SQLException e) {
             logger.error("An error occurred while displaying employee attandnce list.", e);
        }
    }
}