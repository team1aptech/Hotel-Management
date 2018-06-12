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
import java.util.ArrayList;

/**
 *
 * @author Trung
 */
public class Room extends UnicastRemoteObject implements CheckRoom {

    public Room() throws RemoteException {
        super();
    }

    @Override
    public ArrayList checkRoom(int Tang, ArrayList Phong) throws SQLException, ClassNotFoundException, RemoteException {
//        ArrayList Phong = new ArrayList();
        Connection connection = controllerConnectDB.connecDB();
        String request = "select MAPHONG,TINHTRANG from phong inner join Tang on phong.MATANG = Tang.MATANG where Tang.MATANG = ?";
        PreparedStatement stmt = connection.prepareStatement(request);
        stmt.setInt(1, Tang);
        ResultSet rs = stmt.executeQuery();

//        
        while (rs.next()) {
//            str = rs.getString("TINHTRANG");
            String[] array = {rs.getString("MAPHONG"), rs.getString("TINHTRANG")};

            Phong.add(array);
//           
        }
        System.out.println(Phong.size());
        rs.close();
        stmt.close();
        connection.close();
//        
        return Phong;

    }

}
