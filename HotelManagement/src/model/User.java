/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author uphoto
 */
public class User {
    String MANHANVIEN;
    String Username;
    String Pass;

    public User() {
    }

    public User(String MANHANVIEN, String Username, String Pass) {
        this.MANHANVIEN = MANHANVIEN;
        this.Username = Username;
        this.Pass = Pass;
        
    }

    public String getMANHANVIEN() {
        return MANHANVIEN;
    }

    public void setMANHANVIEN(String MANHANVIEN) {
        this.MANHANVIEN = MANHANVIEN;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    
//    public static boolean checkPass(String Username, String Pass) throws SQLException, ClassNotFoundException{
//        Connection connection = controllerConnectDB.connecDB();
//        String request = "Select * from Users where Username = ? and Pass = ?";
//        PreparedStatement stmt = connection.prepareStatement(request);
//        stmt.setString(1, Username);
//        stmt.setString(2, Pass);
//        ResultSet rs = stmt.executeQuery();
//        boolean flag =false;
//        if (rs.next()) flag = true;
//        rs.close();
//        stmt.close();
//        connection.close();
//        return flag;
//    }
//    public static Vector getAllUsers() throws SQLException, ClassNotFoundException {
//        Connection connection = controllerConnectDB.connecDB();
//        
//        String request = "Select MANHANVIEN, Username, Pass from Users";
//        Statement stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery(request);
//        
//        Vector list = new Vector();
//        while (rs.next()) {
//            Vector<String> temp = new Vector<>();
//            
//            temp.add(rs.getString("MANHANVIEN"));
//            temp.add(rs.getString("Username"));
//            temp.add(rs.getString("Pass"));
//            
//            list.add(temp);
//        }
        
//        return list;
//    }
    
}
