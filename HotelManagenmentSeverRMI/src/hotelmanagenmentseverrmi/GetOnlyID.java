/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagenmentseverrmi;

import connection.controllerConnectDB;
import hotelmanagementinterfacermi.CheckGetOnlyID;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author uphoto
 */
public class GetOnlyID extends UnicastRemoteObject implements CheckGetOnlyID {

    public GetOnlyID() throws RemoteException {
        super();
    }

    @Override
    public Vector GetOnlyID() throws SQLException, ClassNotFoundException, RemoteException {
        Connection connection = controllerConnectDB.connecDB();
        String request = "Select MANHANVIEN from NHANVIEN";
        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(request);
        Vector<String> list = new Vector<>();

        while (rs.next()) {
            list.add(rs.getString("MANHANVIEN"));
        }

        return list;
    }

}
