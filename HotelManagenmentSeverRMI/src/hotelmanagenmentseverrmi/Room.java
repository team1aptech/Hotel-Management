/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagenmentseverrmi;

import connection.controllerConnectDB;
import hotelmanagementinterfacermi.CheckRoom;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Trung
 */
public class Room extends UnicastRemoteObject implements CheckRoom {
    public Room() throws RemoteException{
        super();
    }

    @Override
    public String checkRoom(String NameRoom) throws SQLException, ClassNotFoundException, RemoteException {
        Connection connection = controllerConnectDB.connecDB();
        String request = "Select TINHTRANG from phong where MAPHONG = ?";
        PreparedStatement stmt = connection.prepareStatement(request);
        stmt.setString(1, NameRoom);
        ResultSet rs = stmt.executeQuery();
        String str = null ;
        if (rs.next()) {
            str = rs.getString("TINHTRANG");
            rs.close();
            stmt.close();
            connection.close();
            
        }
        return str;
    }

   
    
}
