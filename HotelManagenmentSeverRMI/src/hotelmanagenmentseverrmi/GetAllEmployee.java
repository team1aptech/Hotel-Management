/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagenmentseverrmi;

import connection.controllerConnectDB;
import hotelmanagementinterfacermi.CheckGetAllEmpoyee;
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
public class GetAllEmployee extends UnicastRemoteObject implements CheckGetAllEmpoyee{

    public GetAllEmployee() throws RemoteException {
        super();
    }
    
    @Override
    public Vector GetAllEmpoyee() throws SQLException, ClassNotFoundException, RemoteException {
        Connection connection = controllerConnectDB.connecDB();
        String request = "Select MANHANVIEN, TENNHANVIEN,MACHUCVU,GIOITINH,NGAYSINH,DIACHI,SDT from nhanvien";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(request);

        Vector list = new Vector();
        while (rs.next()) {
            Vector<String> temp = new Vector<>();

            temp.add(rs.getString("MANHANVIEN"));
            temp.add(rs.getString("TENNHANVIEN"));
            temp.add(rs.getString("MACHUCVU"));
            temp.add(rs.getString("GIOITINH"));
            temp.add(rs.getString("NGAYSINH"));
            temp.add(rs.getString("DIACHI"));
            temp.add(rs.getString("SDT"));
            list.add(temp);
            
        }
        return list;
    }
    
}
