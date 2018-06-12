/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagenmentseverrmi;

import connection.controllerConnectDB;
import hotelmanagementinterfacermi.CheckGetAllUser;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author Trung
 */
public class GetAllUsers extends UnicastRemoteObject implements CheckGetAllUser {

    public GetAllUsers() throws RemoteException {
        super();
    }

    @Override
    


    public Vector getAllUsers() throws SQLException, ClassNotFoundException, RemoteException {
        Connection connection = controllerConnectDB.connecDB();
        String request = "Select MANHANVIEN, Username, Pass from Users";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(request);

        Vector list = new Vector();
        while (rs.next()) {
            Vector<String> temp = new Vector<>();

            temp.add(rs.getString("MANHANVIEN"));
            temp.add(rs.getString("Username"));
            temp.add(rs.getString("Pass"));
            list.add(temp);
        }
        return list;
    }
}


