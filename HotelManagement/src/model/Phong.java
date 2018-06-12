/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import hotelmanagementinterfacermi.Customer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import view.JFrameMain;
import static view.JFrameMain.lbPhong;

/**
 *
 * @author Trung
 */
public class Phong {

    String MaPhong;
    int MaTang;
    String TinhTrang;
    String MaLoaiPhong;
    int GiaGio;
    int GiaDem;
    int GiaNgay;

    public Phong() {
    }

    public Phong(String MaPhong, String TinhTrang) {
        this.MaPhong = MaPhong;
        this.TinhTrang = TinhTrang;
    }

    public Phong(String MaPhong, int MaTang, String TinhTrang, String MaLoaiPhong, int GiaGio, int GiaDem, int GiaNgay) {
        this.MaPhong = MaPhong;
        this.MaTang = MaTang;
        this.TinhTrang = TinhTrang;
        this.MaLoaiPhong = MaLoaiPhong;
        this.GiaGio = GiaGio;
        this.GiaDem = GiaDem;
        this.GiaNgay = GiaNgay;
    }

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String MaPhong) {
        this.MaPhong = MaPhong;
    }

    public int getMaTang() {
        return MaTang;
    }

    public void setMaTang(int MaTang) {
        this.MaTang = MaTang;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public String getMaLoaiPhong() {
        return MaLoaiPhong;
    }

    public void setMaLoaiPhong(String MaLoaiPhong) {
        this.MaLoaiPhong = MaLoaiPhong;
    }

    public int getGiaGio() {
        return GiaGio;
    }

    public void setGiaGio(int GiaGio) {
        this.GiaGio = GiaGio;
    }

    public int getGiaDem() {
        return GiaDem;
    }

    public void setGiaDem(int GiaDem) {
        this.GiaDem = GiaDem;
    }

    public int getGiaNgay() {
        return GiaNgay;
    }

    public void setGiaNgay(int GiaNgay) {
        this.GiaNgay = GiaNgay;
    }
    public static void GiaPhong(){
        try {
                    Remote lookup = null;
                    lookup = Naming.lookup("rmi://localhost:1099/customer");

                    Customer customer = (Customer) lookup;
                    Vector GiaPhong;

                    GiaPhong = customer.getGiaPhong(lbPhong.getText());
                    System.out.println(lbPhong.getText());
                    for (Object GiaPhong1 : GiaPhong) {

                        if (view.JFrameMain.jcbGiaPhong.getSelectedIndex() == 0) {
                            view.JFrameMain.jtxtTienPhong.setText((String) GiaPhong.get(0));
                        }
                        if (view.JFrameMain.jcbGiaPhong.getSelectedIndex() == 1) {
                            view.JFrameMain.jtxtTienPhong.setText((String) GiaPhong.get(1));
                        }
                        if (view.JFrameMain.jcbGiaPhong.getSelectedIndex() == 2) {
                            view.JFrameMain.jtxtTienPhong.setText((String) GiaPhong.get(2));
                        }
                        
                    }
                } catch (SQLException | ClassNotFoundException | RemoteException | NotBoundException | MalformedURLException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    public JPanel TaoPhong(String MaPhong, String TinhTrang) {
        JPanel panel = new JPanel();

        

        JLabel label = new JLabel(MaPhong);
        panel.setPreferredSize(new Dimension(56, 67));

        panel.add(label);
        if (TinhTrang.compareToIgnoreCase("TRỐNG") == 0) {
            panel.setBackground(Color.GREEN);
        }
        if (TinhTrang.equals("ĐANG SỬ DỤNG")) {
            panel.setBackground(Color.RED);
        }
        if (TinhTrang.equals("ĐÃ ĐẶT")) {
            panel.setBackground(Color.BLUE);
        }
        panel.setName(MaPhong);
        panel.revalidate();
        panel.repaint();
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.JFrameMain.lbPhong.setText(panel.getName());
                
                
                panel.setBorder(LineBorder.createBlackLineBorder());
                GiaPhong();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
               panel.setBorder(null);
            }
        });

        return panel;
    }

}
