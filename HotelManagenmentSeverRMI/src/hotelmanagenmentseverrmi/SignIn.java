/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagenmentseverrmi;

import connection.controllerConnectDB;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import hotelmanagementinterfacermi.MySignIn;

/**
 *
 * @author Trung
 */
public class SignIn extends UnicastRemoteObject implements MySignIn{
    
    public SignIn() throws RemoteException{
        super();
    }

    @Override
    public boolean checkPass(String Username, String Pass) throws SQLException, ClassNotFoundException, RemoteException {
        System.out.println("dang SignIn");
        System.out.println("dang ket noi database");
        Connection connection = controllerConnectDB.connecDB();
        String request = "Select * from Users where Username = ? and Pass = ?";
        PreparedStatement stmt = connection.prepareStatement(request);
        stmt.setString(1, Username);
        stmt.setString(2, Pass);
        ResultSet rs = stmt.executeQuery();
        boolean flag =false;
        if (rs.next()) flag = true;
        rs.close();
        stmt.close();
        connection.close();
        return flag;
    }

   
    
}
