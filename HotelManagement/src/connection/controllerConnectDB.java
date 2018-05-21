/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uphoto
 */
public class controllerConnectDB {
    public static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String DB_URL = "jdbc:sqlserver://localhost;";
    public static final String DATABASENAME = "databaseName=QLKHACHSAN;";
    public static final String USER = "user=sa;";
    public static final String PASS = "password=123456a@";
    
    public static Connection connecDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL +DATABASENAME +USER +PASS);
        System.out.println("Connect succesfull");
        return connection;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connecDB();
    }
    
    
           
}
