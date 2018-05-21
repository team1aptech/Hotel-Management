/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.controllerConnectDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author uphoto
 */
public class NhanVien {
    String MANHANVIEN;
    String TENNHANVIEN;
    String MACHUCVU;
    byte GIOITINH;
    Date NGAYSINH;
    String DIACHI;
    int SDT;

    public NhanVien() {
    }

    public NhanVien(String MANHANVIEN, String TENNHANVIEN, String MACHUCVU, byte GIOITINH, Date NGAYSINH, String DIACHI, int SDT) {
        this.MANHANVIEN = MANHANVIEN;
        this.TENNHANVIEN = TENNHANVIEN;
        this.MACHUCVU = MACHUCVU;
        this.GIOITINH = GIOITINH;
        this.NGAYSINH = NGAYSINH;
        this.DIACHI = DIACHI;
        this.SDT = SDT;
    }

    public String getMANHANVIEN() {
        return MANHANVIEN;
    }

    public void setMANHANVIEN(String MANHANVIEN) {
        this.MANHANVIEN = MANHANVIEN;
    }

    public String getTENNHANVIEN() {
        return TENNHANVIEN;
    }

    public void setTENNHANVIEN(String TENNHANVIEN) {
        this.TENNHANVIEN = TENNHANVIEN;
    }

    public String getMACHUCVU() {
        return MACHUCVU;
    }

    public void setMACHUCVU(String MACHUCVU) {
        this.MACHUCVU = MACHUCVU;
    }

    public byte getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(byte GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public Date getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(Date NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }
    public static Vector<String> getAllID() throws ClassNotFoundException, SQLException {
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
