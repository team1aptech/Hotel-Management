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
import java.sql.Statement;
import java.util.Vector;
import hotelmanagementinterfacermi.Customer;
/**
 *
 * @author Trung
 */
public class CustomerSever extends UnicastRemoteObject implements Customer{

    public CustomerSever() throws RemoteException {
        super();
    }

    @Override
    public Vector getTenNhanVien(Vector emp) throws SQLException, ClassNotFoundException, RemoteException {

        String sql = "select TENNHANVIEN from nhanvien";
        Connection con = controllerConnectDB.connecDB();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getString("TENNHANVIEN"));
            emp.add(rs.getString("TENNHANVIEN"));
            System.out.println(emp.size());

        }
        System.out.println(emp.size());
        return emp;

    }

    @Override
    public Vector getGiaPhong(String MaPhong) throws SQLException, ClassNotFoundException, RemoteException {
        String sql = "select GIAPHONGMOTGIO,GIAPHONGMOTNGAY,GIAPHONGQUADEM from phong where SOPHONG like ?";
        Connection con = controllerConnectDB.connecDB();
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, MaPhong);
        
        ResultSet rs = pstm.executeQuery();
        Vector GiaPhong = new Vector();
        while (rs.next()) {
            System.out.println(rs.getString("GIAPHONGMOTNGAY"));
            GiaPhong.add(rs.getString("GIAPHONGMOTGIO"));
            GiaPhong.add(rs.getString("GIAPHONGMOTNGAY"));
            GiaPhong.add(rs.getString("GIAPHONGQUADEM"));
            System.out.println(GiaPhong.size());

        }
        System.out.println(GiaPhong.size());
        return GiaPhong;
    }

}
