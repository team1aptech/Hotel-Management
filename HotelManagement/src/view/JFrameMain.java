/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import hotelmanagementinterfacermi.CheckGetAllEmpoyee;
import hotelmanagementinterfacermi.CheckGetAllUser;
import hotelmanagementinterfacermi.CheckGetOnlyID;
import hotelmanagementinterfacermi.CheckRoom;
import hotelmanagementinterfacermi.Customer;
import hotelmanagementinterfacermi.MySignIn;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import static java.rmi.Naming.lookup;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.runtime.Undefined.lookup;
import model.Phong;

/**
 *
 * @author uphoto
 */
public class JFrameMain extends javax.swing.JFrame {

    int mouseX;
    int mouseY;

    public void CreatePhongInfor(int tang, JPanel panel) {
        Remote lookup = null;
        ArrayList PhongInfo = new ArrayList();
        try {
            lookup = Naming.lookup("rmi://localhost:1099/room");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        CheckRoom checkroom = (CheckRoom) lookup;
        try {
            PhongInfo = checkroom.checkRoom(tang, PhongInfo);

        } catch (SQLException | ClassNotFoundException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < PhongInfo.size(); i++) {

            String[] emp = (String[]) PhongInfo.get(i);
            Phong phong = new Phong();

            panel.add(phong.TaoPhong(emp[0], emp[1]));
            System.out.println(emp[0]);
            System.out.println(emp[1]);
        }
    }

    public static void refreshUsers() throws SQLException, ClassNotFoundException, RemoteException {
        Remote lookup = null;

        try {
            lookup = Naming.lookup("rmi://localhost:1099/users");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        CheckGetAllUser user = (CheckGetAllUser) lookup;
        Vector<String> title = new Vector<>();
        title.add("Mã Nhân Viên");
        title.add("User");
        title.add("Password");

        tblUserList.setModel(new DefaultTableModel(user.getAllUsers(), title));
    }

    public static void refreshAllEmployee() throws SQLException, ClassNotFoundException, RemoteException {
        Remote lookup = null;

        try {
            lookup = Naming.lookup("rmi://localhost:1099/emp");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        CheckGetAllEmpoyee emp = (CheckGetAllEmpoyee) lookup;

        Vector<String> title = new Vector<>();

        title.add("MANHANVIEN");
        title.add("TENNHANVIEN");
        title.add("MACHUCVU");
        title.add("GIOITINH");
        title.add("NGAYSINH");
        title.add("DIACHI");
        title.add("SDT");

//        tblEmpList.setModel(new DefaultTableModel(emp.GetAllEmpoyee(), title));
        tblEmpList.setModel(new DefaultTableModel(emp.GetAllEmpoyee(), title));
    }

    public static void refreshEmployeeID() throws SQLException, ClassNotFoundException, RemoteException {
        Remote lookup = null;
        try {
            lookup = Naming.lookup("rmi://localhost:1099/empID");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        CheckGetOnlyID empID = (CheckGetOnlyID) lookup;
        Vector<String> list = empID.GetOnlyID();
        cbbNewUser.removeAllItems();
        for (String string : list) {
            cbbNewUser.addItem(string);

        }
    }

    class checkEmptyNewUser extends Thread {

        @Override
        public void run() {
            System.out.println("Thread started");
            while (true) {
                try {
                    sleep(1);
                    if (txtNewUserAddUser.getText().trim().isEmpty() || txtNewUserAddPass.getText().trim().isEmpty()) {
                        btnNewUserAddUser.setEnabled(false);

                    } else {
                        btnNewUserAddUser.setEnabled(true);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(loginScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    checkEmptyNewUser checkEmptyNewUser = new checkEmptyNewUser();

    public JFrameMain() {
        initComponents();
        checkEmptyNewUser.start();
        Remote lookup = null;
        try {
            refreshUsers();
        } catch (SQLException | ClassNotFoundException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            refreshAllEmployee();
        } catch (SQLException | ClassNotFoundException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            refreshEmployeeID();
        } catch (SQLException | ClassNotFoundException | RemoteException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        jcbGiaPhong.addItem("Giá Theo Giờ");
        jcbGiaPhong.addItem("Giá Theo Đêm");
        jcbGiaPhong.addItem("Giá Theo Ngày");
        CreatePhongInfor(2, jPanel30);
        CreatePhongInfor(3, jPanel39);
        CreatePhongInfor(4, jPanel43);

        try {

            lookup = Naming.lookup("rmi://localhost:1099/customer");
            Customer customer = (Customer) lookup;
            Vector temp = new Vector();
            temp = customer.getTenNhanVien(temp);

            for (int i = 0; i < temp.size(); i++) {

                jcbTenNhanVien.addItem((String) temp.get(i));
            }

        } catch (NotBoundException | MalformedURLException | RemoteException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

//Get Giá Phòng 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnMain = new javax.swing.JPanel();
        TopMenuPanel = new keeptoo.KGradientPanel();
        image = new javax.swing.JLabel();
        programName = new javax.swing.JLabel();
        diachi = new javax.swing.JLabel();
        closeWindow = new javax.swing.JLabel();
        minimizerWindow = new javax.swing.JLabel();
        maximizerWindow = new javax.swing.JLabel();
        LeftMenuPanel = new javax.swing.JLayeredPane();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        containPanel = new javax.swing.JPanel();
        roomManagement = new javax.swing.JPanel();
        CustomerID1 = new javax.swing.JPanel();
        jLabel130 = new javax.swing.JLabel();
        SignUpCustomer1 = new javax.swing.JPanel();
        jLabel131 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel132 = new javax.swing.JLabel();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jLabel133 = new javax.swing.JLabel();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jLabel134 = new javax.swing.JLabel();
        jcbTenNhanVien = new javax.swing.JComboBox<>();
        jLabel135 = new javax.swing.JLabel();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jLabel136 = new javax.swing.JLabel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jLabel137 = new javax.swing.JLabel();
        jcbGiaPhong = new javax.swing.JComboBox<>();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jFormattedTextField17 = new javax.swing.JFormattedTextField();
        jLabel140 = new javax.swing.JLabel();
        jtxtNgayDi = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lbPhong = new javax.swing.JLabel();
        RoomStatus = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel30 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel43 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Cash1 = new javax.swing.JPanel();
        jtxtDatTruoc = new javax.swing.JFormattedTextField();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jtxtTienPhong = new javax.swing.JFormattedTextField();
        jLabel143 = new javax.swing.JLabel();
        jFormattedTextField21 = new javax.swing.JFormattedTextField();
        jLabel144 = new javax.swing.JLabel();
        jFormattedTextField22 = new javax.swing.JFormattedTextField();
        jLabel145 = new javax.swing.JLabel();
        jFormattedTextField23 = new javax.swing.JFormattedTextField();
        jLabel146 = new javax.swing.JLabel();
        jFormattedTextField24 = new javax.swing.JFormattedTextField();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        pnlSetting = new keeptoo.KGradientPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        personManagement = new javax.swing.JPanel();
        jLabel149 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnOptionAccount = new javax.swing.JButton();
        btnOptionUser = new javax.swing.JButton();
        pnlOption = new javax.swing.JPanel();
        pnlAccount = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpList = new javax.swing.JTable();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        btnOptionUser4 = new javax.swing.JButton();
        btnOptionUser5 = new javax.swing.JButton();
        btnOptionUser6 = new javax.swing.JButton();
        pnlNewAcc = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        btnNewAccAddAcc = new javax.swing.JButton();
        btnNewAccClear = new javax.swing.JButton();
        pnlUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUserList = new javax.swing.JTable();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        btnOptionUser1 = new javax.swing.JButton();
        btnOptionUser2 = new javax.swing.JButton();
        btnOptionUser3 = new javax.swing.JButton();
        pnlNewUser = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbbNewUser = new javax.swing.JComboBox<>();
        txtNewUserAddUser = new javax.swing.JTextField();
        txtNewUserAddPass = new javax.swing.JPasswordField();
        btnNewUserAddUser = new javax.swing.JButton();
        btnNewUserClear = new javax.swing.JButton();
        warehouseManagement = new javax.swing.JPanel();
        MenuTopWareHouse = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        MenuLeftWareHouse = new keeptoo.KGradientPanel();
        pnlWater = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        pnlFood = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        pnlSouvenir = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        WarehouseContent = new javax.swing.JPanel();
        SouvenirManagement = new javax.swing.JPanel();
        MenuTopWareHouse3 = new keeptoo.KGradientPanel();
        jLabel54 = new javax.swing.JLabel();
        moreItems2 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        cbbMatHang2 = new javax.swing.JComboBox<>();
        jTextField10 = new javax.swing.JTextField();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        btnLoginLogin4 = new javax.swing.JButton();
        btnLoginLogin5 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        FoodManagement = new javax.swing.JPanel();
        MenuTopWareHouse2 = new keeptoo.KGradientPanel();
        jLabel50 = new javax.swing.JLabel();
        moreItems1 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        cbbMatHang1 = new javax.swing.JComboBox<>();
        jTextField9 = new javax.swing.JTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        btnLoginLogin2 = new javax.swing.JButton();
        btnLoginLogin3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        WaterManagement = new javax.swing.JPanel();
        MenuTopWareHouse1 = new keeptoo.KGradientPanel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        moreItems = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cbbMatHang = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        btnLoginLogin = new javax.swing.JButton();
        btnLoginLogin1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aptech Hotel Management");
        setUndecorated(true);

        jpnMain.setBackground(new java.awt.Color(255, 255, 255));
        jpnMain.setOpaque(false);
        jpnMain.setPreferredSize(new java.awt.Dimension(1450, 890));
        jpnMain.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpnMainMouseDragged(evt);
            }
        });
        jpnMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpnMainMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpnMainMouseReleased(evt);
            }
        });

        TopMenuPanel.setkEndColor(new java.awt.Color(0, 204, 204));
        TopMenuPanel.setkGradientFocus(1200);
        TopMenuPanel.setkStartColor(new java.awt.Color(153, 0, 255));
        TopMenuPanel.setPreferredSize(new java.awt.Dimension(1400, 52));

        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hotel-icon-10-jmain.png"))); // NOI18N

        programName.setFont(new java.awt.Font("Sinhala MN", 1, 40)); // NOI18N
        programName.setForeground(new java.awt.Color(255, 255, 255));
        programName.setText("APTECH HOTEL MANAGEMENT");

        diachi.setFont(new java.awt.Font("Khmer MN", 1, 18)); // NOI18N
        diachi.setForeground(new java.awt.Color(255, 255, 255));
        diachi.setText("38 YÊN BÁI, HẢI CHÂU, ĐÀ NẴNG");

        closeWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-delete-20.png"))); // NOI18N
        closeWindow.setText("jLabel4");
        closeWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeWindowMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                closeWindowMouseReleased(evt);
            }
        });

        minimizerWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-minus-24.png"))); // NOI18N
        minimizerWindow.setText("jLabel4");
        minimizerWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minimizerWindowMousePressed(evt);
            }
        });

        maximizerWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-unchecked-checkbox-17.png"))); // NOI18N
        maximizerWindow.setText("jLabel4");
        maximizerWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                maximizerWindowMousePressed(evt);
            }
        });

        javax.swing.GroupLayout TopMenuPanelLayout = new javax.swing.GroupLayout(TopMenuPanel);
        TopMenuPanel.setLayout(TopMenuPanelLayout);
        TopMenuPanelLayout.setHorizontalGroup(
            TopMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopMenuPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(diachi)
                .addGap(192, 192, 192)
                .addComponent(programName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                .addComponent(minimizerWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(maximizerWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        TopMenuPanelLayout.setVerticalGroup(
            TopMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(programName)
                .addComponent(diachi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(TopMenuPanelLayout.createSequentialGroup()
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(TopMenuPanelLayout.createSequentialGroup()
                .addGroup(TopMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimizerWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maximizerWindow))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        LeftMenuPanel.setBackground(new java.awt.Color(204, 204, 0));

        kGradientPanel2.setkEndColor(new java.awt.Color(0, 204, 204));
        kGradientPanel2.setkGradientFocus(300);

        jPanel1.setBackground(new java.awt.Color(151, 2, 254));
        jPanel1.setOpaque(false);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel1MouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("QUẢN LÝ PHÒNG");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-living-room-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(365, 365, 365))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setOpaque(false);
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel2MouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("QUẢN LÝ NHÂN VIÊN");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel9.setPreferredSize(new java.awt.Dimension(201, 29));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-person-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setOpaque(false);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel3MouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("QUẢN LÝ KHO");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-warehouse-filled-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setOpaque(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel4MouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("THỐNG KÊ BÁO CÁO");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-statistics-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setOpaque(false);
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel5MouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("CÀI ĐẶT");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-settings-30.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(440, Short.MAX_VALUE))
        );

        LeftMenuPanel.setLayer(kGradientPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout LeftMenuPanelLayout = new javax.swing.GroupLayout(LeftMenuPanel);
        LeftMenuPanel.setLayout(LeftMenuPanelLayout);
        LeftMenuPanelLayout.setHorizontalGroup(
            LeftMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftMenuPanelLayout.createSequentialGroup()
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );
        LeftMenuPanelLayout.setVerticalGroup(
            LeftMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftMenuPanelLayout.createSequentialGroup()
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        containPanel.setBackground(new java.awt.Color(255, 255, 255));
        containPanel.setLayout(new java.awt.CardLayout());

        roomManagement.setBackground(new java.awt.Color(255, 255, 255));
        roomManagement.setPreferredSize(new java.awt.Dimension(1198, 890));

        CustomerID1.setBackground(new java.awt.Color(0, 204, 204));
        CustomerID1.setPreferredSize(new java.awt.Dimension(626, 890));

        jLabel130.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(153, 0, 255));
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText("THÔNG TIN KHÁCH HÀNG");

        SignUpCustomer1.setBackground(new java.awt.Color(0, 204, 204));

        jLabel131.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(255, 255, 255));
        jLabel131.setText("Tên Khách");

        jTextField2.setBackground(new java.awt.Color(0, 204, 204));
        jTextField2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jTextField2.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField2.setOpaque(false);

        jLabel132.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setText("CMND / Passport");

        jFormattedTextField13.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField13.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField13.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jFormattedTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField13ActionPerformed(evt);
            }
        });

        jLabel133.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(255, 255, 255));
        jLabel133.setText("Phone");

        jFormattedTextField14.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField14.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField14.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel134.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(255, 255, 255));
        jLabel134.setText("Nhân Viên");

        jcbTenNhanVien.setBackground(new java.awt.Color(0, 204, 204));
        jcbTenNhanVien.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jcbTenNhanVien.setMaximumRowCount(20);
        jcbTenNhanVien.setBorder(null);
        jcbTenNhanVien.setOpaque(false);

        jLabel135.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setText("Mã Số Thuế");

        jFormattedTextField15.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField15.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField15.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel136.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(255, 255, 255));
        jLabel136.setText("Số Người");

        jFormattedTextField16.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField16.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField16.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel137.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(255, 255, 255));
        jLabel137.setText("Giá");

        jcbGiaPhong.setBackground(new java.awt.Color(0, 204, 204));
        jcbGiaPhong.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jcbGiaPhong.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jcbGiaPhong.setOpaque(false);
        jcbGiaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbGiaPhongActionPerformed(evt);
            }
        });

        jLabel138.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(255, 255, 255));
        jLabel138.setText("Phòng");

        jLabel139.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setText("Ngày Vào");

        jFormattedTextField17.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField17.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField17.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel140.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setText("Ngày Đi");

        jtxtNgayDi.setBackground(new java.awt.Color(0, 204, 204));
        jtxtNgayDi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jtxtNgayDi.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNgayDi.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jtxtNgayDi.setCaretColor(new java.awt.Color(255, 255, 255));
        jtxtNgayDi.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jtxtNgayDi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNgayDiActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 204, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-direction-40.png"))); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setContentAreaFilled(false);

        jButton4.setBackground(new java.awt.Color(0, 204, 204));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-merge-vertical-40.png"))); // NOI18N
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setContentAreaFilled(false);

        lbPhong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbPhong.setForeground(new java.awt.Color(240, 240, 240));
        lbPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbPhongMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SignUpCustomer1Layout = new javax.swing.GroupLayout(SignUpCustomer1);
        SignUpCustomer1.setLayout(SignUpCustomer1Layout);
        SignUpCustomer1Layout.setHorizontalGroup(
            SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                        .addComponent(jLabel134)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel131)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132)
                    .addComponent(jFormattedTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel133)
                    .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel135)
                            .addComponent(jLabel136)
                            .addComponent(jFormattedTextField16)
                            .addComponent(jFormattedTextField15)
                            .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel138)
                                    .addComponent(jLabel137))
                                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jcbGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbPhong)))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SignUpCustomer1Layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addGap(18, 18, 18)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)))
                    .addComponent(jLabel140)
                    .addComponent(jtxtNgayDi, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
        );

        SignUpCustomer1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcbGiaPhong, lbPhong});

        SignUpCustomer1Layout.setVerticalGroup(
            SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SignUpCustomer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                        .addComponent(jLabel135)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel136)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel137))
                            .addComponent(jcbGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel138)
                            .addComponent(lbPhong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)))
                    .addGroup(SignUpCustomer1Layout.createSequentialGroup()
                        .addComponent(jLabel131)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel132)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel133)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel134)
                            .addComponent(jcbTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel139)
                    .addComponent(jLabel140))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SignUpCustomer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtNgayDi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SignUpCustomer1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jcbGiaPhong, lbPhong});

        javax.swing.GroupLayout CustomerID1Layout = new javax.swing.GroupLayout(CustomerID1);
        CustomerID1.setLayout(CustomerID1Layout);
        CustomerID1Layout.setHorizontalGroup(
            CustomerID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerID1Layout.createSequentialGroup()
                .addGroup(CustomerID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SignUpCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CustomerID1Layout.setVerticalGroup(
            CustomerID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerID1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel130)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SignUpCustomer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        RoomStatus.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setText("Tầng 2: ");

        jLabel29.setText("Tầng 3: ");

        jScrollPane6.setBorder(null);
        jScrollPane6.setName("jsctang2"); // NOI18N

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel30.setName("tang2"); // NOI18N
        jPanel30.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        jScrollPane6.setViewportView(jPanel30);

        jLabel39.setText("Tầng 4:");

        jScrollPane7.setBorder(null);
        jScrollPane7.setName("jsctang3"); // NOI18N

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        jScrollPane7.setViewportView(jPanel39);

        jScrollPane8.setBorder(null);
        jScrollPane8.setName("jsctang4"); // NOI18N

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel43.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        jScrollPane8.setViewportView(jPanel43);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 20);
        flowLayout1.setAlignOnBaseline(true);
        jPanel6.setLayout(flowLayout1);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Phongtrong.png"))); // NOI18N
        jLabel17.setText("Phòng trống");
        jPanel6.add(jLabel17);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/phongcokhach.png"))); // NOI18N
        jLabel19.setText("Phòng có khách");
        jPanel6.add(jLabel19);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Phongquahan.png"))); // NOI18N
        jLabel18.setText("Phòng quá hạn");
        jPanel6.add(jLabel18);

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/phongdangsua.png"))); // NOI18N
        jLabel21.setText("Phòng đang sửa");
        jPanel6.add(jLabel21);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Phongdadat.png"))); // NOI18N
        jLabel20.setText("Phòng đã đặt");
        jPanel6.add(jLabel20);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/phongban.png"))); // NOI18N
        jLabel22.setText("Phòng bẩn");
        jPanel6.add(jLabel22);

        javax.swing.GroupLayout RoomStatusLayout = new javax.swing.GroupLayout(RoomStatus);
        RoomStatus.setLayout(RoomStatusLayout);
        RoomStatusLayout.setHorizontalGroup(
            RoomStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomStatusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(RoomStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RoomStatusLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        RoomStatusLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane6, jScrollPane7, jScrollPane8});

        RoomStatusLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel28, jLabel29, jLabel39});

        RoomStatusLayout.setVerticalGroup(
            RoomStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(467, Short.MAX_VALUE))
        );

        RoomStatusLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane6, jScrollPane7, jScrollPane8});

        RoomStatusLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel28, jLabel29, jLabel39});

        Cash1.setBackground(new java.awt.Color(0, 204, 204));
        Cash1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtDatTruoc.setEditable(false);
        jtxtDatTruoc.setBackground(new java.awt.Color(0, 204, 204));
        jtxtDatTruoc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jtxtDatTruoc.setForeground(new java.awt.Color(255, 255, 255));
        jtxtDatTruoc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jtxtDatTruoc.setCaretColor(new java.awt.Color(255, 255, 255));
        jtxtDatTruoc.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Cash1.add(jtxtDatTruoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 224, -1));

        jLabel141.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setText("Đặt Trước");
        Cash1.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        jLabel142.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setText("Tiền Phòng");
        Cash1.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        jtxtTienPhong.setEditable(false);
        jtxtTienPhong.setBackground(new java.awt.Color(0, 204, 204));
        jtxtTienPhong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jtxtTienPhong.setForeground(new java.awt.Color(255, 255, 255));
        jtxtTienPhong.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jtxtTienPhong.setCaretColor(new java.awt.Color(255, 255, 255));
        jtxtTienPhong.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Cash1.add(jtxtTienPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 224, -1));

        jLabel143.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setText("Phí Dịch Vụ");
        Cash1.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, -1, -1));

        jFormattedTextField21.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField21.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField21.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField21.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Cash1.add(jFormattedTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 224, -1));

        jLabel144.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setText("Giảm Giá Phòng");
        Cash1.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, -1, -1));

        jFormattedTextField22.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField22.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField22.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField22.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField22.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Cash1.add(jFormattedTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 224, -1));

        jLabel145.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("Giảm Giá Khác");
        Cash1.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));

        jFormattedTextField23.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField23.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField23.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField23.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField23.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Cash1.add(jFormattedTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 224, -1));

        jLabel146.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("Tổng Thanh Toán");
        Cash1.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 290, -1, -1));

        jFormattedTextField24.setBackground(new java.awt.Color(0, 204, 204));
        jFormattedTextField24.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jFormattedTextField24.setForeground(new java.awt.Color(255, 255, 255));
        jFormattedTextField24.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0"))));
        jFormattedTextField24.setCaretColor(new java.awt.Color(255, 255, 255));
        jFormattedTextField24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Cash1.add(jFormattedTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 224, -1));

        jLabel147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-purchase-order-96.png"))); // NOI18N
        jLabel147.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Cash1.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel148.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-checkout-96.png"))); // NOI18N
        Cash1.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        javax.swing.GroupLayout roomManagementLayout = new javax.swing.GroupLayout(roomManagement);
        roomManagement.setLayout(roomManagementLayout);
        roomManagementLayout.setHorizontalGroup(
            roomManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomManagementLayout.createSequentialGroup()
                .addComponent(RoomStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roomManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomManagementLayout.createSequentialGroup()
                        .addComponent(CustomerID1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addGroup(roomManagementLayout.createSequentialGroup()
                        .addComponent(Cash1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        roomManagementLayout.setVerticalGroup(
            roomManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomManagementLayout.createSequentialGroup()
                .addComponent(CustomerID1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cash1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
            .addComponent(RoomStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        containPanel.add(roomManagement, "roomManagement");

        pnlSetting.setkEndColor(new java.awt.Color(0, 204, 204));

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("ĐỔI MẬT KHẨU NGƯỜI DÙNG");

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-user-account-60.png"))); // NOI18N

        jComboBox2.setBackground(new java.awt.Color(180, 60, 240));
        jComboBox2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jComboBox2.setOpaque(false);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-key-60.png"))); // NOI18N

        jPasswordField2.setBackground(new java.awt.Color(128, 102, 229));
        jPasswordField2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jPasswordField2.setForeground(new java.awt.Color(255, 255, 255));
        jPasswordField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPasswordField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 2, 0));
        jPasswordField2.setCaretColor(new java.awt.Color(255, 255, 255));
        jPasswordField2.setOpaque(false);

        javax.swing.GroupLayout pnlSettingLayout = new javax.swing.GroupLayout(pnlSetting);
        pnlSetting.setLayout(pnlSettingLayout);
        pnlSettingLayout.setHorizontalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60))
                .addGap(39, 39, 39)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2, 0, 230, Short.MAX_VALUE)
                    .addComponent(jPasswordField2))
                .addContainerGap(427, Short.MAX_VALUE))
        );
        pnlSettingLayout.setVerticalGroup(
            pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSettingLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel59))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel60))
                    .addGroup(pnlSettingLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(629, Short.MAX_VALUE))
        );

        containPanel.add(pnlSetting, "pnlSetting");

        personManagement.setBackground(new java.awt.Color(255, 255, 255));
        personManagement.setPreferredSize(new java.awt.Dimension(1198, 890));

        jLabel149.setFont(new java.awt.Font("Sinhala MN", 1, 33)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(153, 0, 255));
        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel149.setText("CẬP NHẬT THÔNG TIN NHÂN VIÊN");

        kGradientPanel1.setkEndColor(new java.awt.Color(0, 204, 204));
        kGradientPanel1.setkGradientFocus(800);
        kGradientPanel1.setkStartColor(new java.awt.Color(153, 0, 255));

        btnOptionAccount.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionAccount.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        btnOptionAccount.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-user-account-40.png"))); // NOI18N
        btnOptionAccount.setText("Account");
        btnOptionAccount.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionAccount.setBorderPainted(false);
        btnOptionAccount.setContentAreaFilled(false);
        btnOptionAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionAccountMouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOptionAccountMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOptionAccountMouseEntered(evt);
            }
        });
        btnOptionAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionAccountActionPerformed(evt);
            }
        });

        btnOptionUser.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        btnOptionUser.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-customer-40.png"))); // NOI18N
        btnOptionUser.setText("Users");
        btnOptionUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser.setBorderPainted(false);
        btnOptionUser.setContentAreaFilled(false);
        btnOptionUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUserMouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOptionUserMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOptionUserMouseEntered(evt);
            }
        });
        btnOptionUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOptionAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146)
                .addComponent(btnOptionUser, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(316, 316, 316))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnOptionAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOptionUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlOption.setBackground(new java.awt.Color(255, 255, 255));
        pnlOption.setPreferredSize(new java.awt.Dimension(1198, 825));
        pnlOption.setLayout(new java.awt.CardLayout());

        pnlAccount.setBackground(new java.awt.Color(255, 255, 255));

        tblEmpList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblEmpList);

        kGradientPanel4.setkEndColor(new java.awt.Color(0, 204, 204));
        kGradientPanel4.setkGradientFocus(800);
        kGradientPanel4.setkStartColor(new java.awt.Color(153, 0, 255));

        btnOptionUser4.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOptionUser4.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser4.setText("Cập Nhật");
        btnOptionUser4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser4.setBorderPainted(false);
        btnOptionUser4.setContentAreaFilled(false);
        btnOptionUser4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUser4MouseReleased(evt);
            }
        });
        btnOptionUser4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUser4ActionPerformed(evt);
            }
        });

        btnOptionUser5.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOptionUser5.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser5.setText("Sửa");
        btnOptionUser5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser5.setBorderPainted(false);
        btnOptionUser5.setContentAreaFilled(false);
        btnOptionUser5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUser5MouseReleased(evt);
            }
        });
        btnOptionUser5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUser5ActionPerformed(evt);
            }
        });

        btnOptionUser6.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOptionUser6.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser6.setText("Xóa");
        btnOptionUser6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser6.setBorderPainted(false);
        btnOptionUser6.setContentAreaFilled(false);
        btnOptionUser6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUser6MouseReleased(evt);
            }
        });
        btnOptionUser6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUser6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(btnOptionUser5, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addComponent(btnOptionUser6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(btnOptionUser4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGroup(kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOptionUser4)
                    .addComponent(btnOptionUser5)
                    .addComponent(btnOptionUser6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlNewAcc.setBackground(new java.awt.Color(255, 255, 255));
        pnlNewAcc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo Tài Khoản Mới", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(153, 51, 255))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 51, 255));
        jLabel4.setText("Mã Nhân Viên");

        jTextField3.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(153, 51, 255));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jTextField3.setCaretColor(new java.awt.Color(153, 51, 255));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 51, 255));
        jLabel23.setText("Họ Tên ");

        jTextField4.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(153, 51, 255));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jTextField4.setCaretColor(new java.awt.Color(153, 51, 255));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 51, 255));
        jLabel24.setText("Mã Chức Vụ");

        jTextField5.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(153, 51, 255));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jTextField5.setCaretColor(new java.awt.Color(153, 51, 255));

        jTextField6.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(153, 51, 255));
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jTextField6.setCaretColor(new java.awt.Color(153, 51, 255));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 51, 255));
        jLabel25.setText("Giới Tính");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(153, 51, 255));
        jLabel26.setText("Ngày Sinh");

        jTextField8.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(153, 51, 255));
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jTextField8.setCaretColor(new java.awt.Color(153, 51, 255));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(153, 51, 255));
        jLabel27.setText("Địa Chỉ");

        jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(153, 51, 255));
        jLabel30.setText("Số Điện Thoại");

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jFormattedTextField1.setForeground(new java.awt.Color(153, 51, 255));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1.setCaretColor(new java.awt.Color(153, 51, 255));

        jFormattedTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        jFormattedTextField2.setForeground(new java.awt.Color(153, 51, 255));
        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField2.setCaretColor(new java.awt.Color(153, 51, 255));

        btnNewAccAddAcc.setBackground(new java.awt.Color(255, 255, 255));
        btnNewAccAddAcc.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnNewAccAddAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-checkmark-40.png"))); // NOI18N
        btnNewAccAddAcc.setText("Tạo Mới");
        btnNewAccAddAcc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNewAccAddAcc.setBorderPainted(false);
        btnNewAccAddAcc.setContentAreaFilled(false);

        btnNewAccClear.setBackground(new java.awt.Color(255, 255, 255));
        btnNewAccClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnNewAccClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-broom-40.png"))); // NOI18N
        btnNewAccClear.setText("Nhập Lại");
        btnNewAccClear.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNewAccClear.setBorderPainted(false);
        btnNewAccClear.setContentAreaFilled(false);

        javax.swing.GroupLayout pnlNewAccLayout = new javax.swing.GroupLayout(pnlNewAcc);
        pnlNewAcc.setLayout(pnlNewAccLayout);
        pnlNewAccLayout.setHorizontalGroup(
            pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewAccLayout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(120, 120, 120)
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNewAccAddAcc)
                    .addComponent(btnNewAccClear))
                .addGap(129, 129, 129))
        );

        pnlNewAccLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jFormattedTextField1, jFormattedTextField2, jTextField3, jTextField4, jTextField5, jTextField6, jTextField8});

        pnlNewAccLayout.setVerticalGroup(
            pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewAccLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewAccAddAcc)))
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNewAccLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlNewAccLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnNewAccClear)))
                .addGap(24, 24, 24)
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewAccLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlNewAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAccountLayout = new javax.swing.GroupLayout(pnlAccount);
        pnlAccount.setLayout(pnlAccountLayout);
        pnlAccountLayout.setHorizontalGroup(
            pnlAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlNewAcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlAccountLayout.setVerticalGroup(
            pnlAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccountLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNewAcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        pnlOption.add(pnlAccount, "pnlAccount");

        pnlUser.setBackground(new java.awt.Color(255, 255, 255));

        tblUserList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblUserList);

        kGradientPanel3.setkEndColor(new java.awt.Color(0, 204, 204));
        kGradientPanel3.setkGradientFocus(800);
        kGradientPanel3.setkStartColor(new java.awt.Color(153, 0, 255));

        btnOptionUser1.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOptionUser1.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser1.setText("Cập Nhật");
        btnOptionUser1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser1.setBorderPainted(false);
        btnOptionUser1.setContentAreaFilled(false);
        btnOptionUser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUser1MouseReleased(evt);
            }
        });
        btnOptionUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUser1ActionPerformed(evt);
            }
        });

        btnOptionUser2.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOptionUser2.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser2.setText("Sửa");
        btnOptionUser2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser2.setBorderPainted(false);
        btnOptionUser2.setContentAreaFilled(false);
        btnOptionUser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUser2MouseReleased(evt);
            }
        });
        btnOptionUser2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUser2ActionPerformed(evt);
            }
        });

        btnOptionUser3.setBackground(new java.awt.Color(255, 255, 255));
        btnOptionUser3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnOptionUser3.setForeground(new java.awt.Color(255, 255, 255));
        btnOptionUser3.setText("Xóa");
        btnOptionUser3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnOptionUser3.setBorderPainted(false);
        btnOptionUser3.setContentAreaFilled(false);
        btnOptionUser3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnOptionUser3MouseReleased(evt);
            }
        });
        btnOptionUser3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionUser3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(btnOptionUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addComponent(btnOptionUser3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(btnOptionUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        kGradientPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOptionUser1, btnOptionUser2, btnOptionUser3});

        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOptionUser1)
                    .addComponent(btnOptionUser2)
                    .addComponent(btnOptionUser3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        kGradientPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnOptionUser1, btnOptionUser2, btnOptionUser3});

        pnlNewUser.setBackground(new java.awt.Color(255, 255, 255));
        pnlNewUser.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo User Mới", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(153, 51, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 51, 255));
        jLabel1.setText("Mã Nhân Viên");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 51, 255));
        jLabel2.setText("User Name");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 51, 255));
        jLabel3.setText("Password");

        cbbNewUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNewUserAddUser.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        txtNewUserAddUser.setForeground(new java.awt.Color(153, 51, 255));
        txtNewUserAddUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewUserAddUser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));

        txtNewUserAddPass.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        txtNewUserAddPass.setForeground(new java.awt.Color(153, 51, 255));
        txtNewUserAddPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewUserAddPass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 51, 255)));
        txtNewUserAddPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewUserAddPassActionPerformed(evt);
            }
        });

        btnNewUserAddUser.setBackground(new java.awt.Color(255, 255, 255));
        btnNewUserAddUser.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnNewUserAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-checkmark-40.png"))); // NOI18N
        btnNewUserAddUser.setText("Tạo Mới");
        btnNewUserAddUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNewUserAddUser.setBorderPainted(false);
        btnNewUserAddUser.setContentAreaFilled(false);
        btnNewUserAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUserAddUserActionPerformed(evt);
            }
        });

        btnNewUserClear.setBackground(new java.awt.Color(255, 255, 255));
        btnNewUserClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnNewUserClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-broom-40.png"))); // NOI18N
        btnNewUserClear.setText("Nhập Lại");
        btnNewUserClear.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnNewUserClear.setBorderPainted(false);
        btnNewUserClear.setContentAreaFilled(false);
        btnNewUserClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnNewUserClearMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlNewUserLayout = new javax.swing.GroupLayout(pnlNewUser);
        pnlNewUser.setLayout(pnlNewUserLayout);
        pnlNewUserLayout.setHorizontalGroup(
            pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewUserLayout.createSequentialGroup()
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNewUserAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewUserClear, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlNewUserLayout.createSequentialGroup()
                        .addGap(301, 301, 301)
                        .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbNewUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNewUserAddUser)
                            .addComponent(txtNewUserAddPass, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNewUserLayout.setVerticalGroup(
            pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewUserLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(txtNewUserAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNewUserAddPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlNewUserLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(pnlNewUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewUserAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewUserClear, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlUserLayout = new javax.swing.GroupLayout(pnlUser);
        pnlUser.setLayout(pnlUserLayout);
        pnlUserLayout.setHorizontalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(pnlNewUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlUserLayout.setVerticalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNewUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        pnlOption.add(pnlUser, "pnlUser");

        javax.swing.GroupLayout personManagementLayout = new javax.swing.GroupLayout(personManagement);
        personManagement.setLayout(personManagementLayout);
        personManagementLayout.setHorizontalGroup(
            personManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlOption, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personManagementLayout.createSequentialGroup()
                .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        personManagementLayout.setVerticalGroup(
            personManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personManagementLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel149, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        containPanel.add(personManagement, "personManagement");

        warehouseManagement.setPreferredSize(new java.awt.Dimension(1198, 890));

        MenuTopWareHouse.setkEndColor(new java.awt.Color(0, 204, 204));
        MenuTopWareHouse.setkStartColor(new java.awt.Color(153, 51, 255));

        jLabel5.setFont(new java.awt.Font("Sinhala MN", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("QUẢN LÝ KHO");

        javax.swing.GroupLayout MenuTopWareHouseLayout = new javax.swing.GroupLayout(MenuTopWareHouse);
        MenuTopWareHouse.setLayout(MenuTopWareHouseLayout);
        MenuTopWareHouseLayout.setHorizontalGroup(
            MenuTopWareHouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuTopWareHouseLayout.setVerticalGroup(
            MenuTopWareHouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuLeftWareHouse.setkEndColor(new java.awt.Color(0, 204, 204));
        MenuLeftWareHouse.setkGradientFocus(100);
        MenuLeftWareHouse.setkStartColor(new java.awt.Color(153, 51, 255));

        pnlWater.setForeground(new java.awt.Color(255, 255, 255));
        pnlWater.setOpaque(false);
        pnlWater.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlWaterMouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlWaterMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlWaterMouseEntered(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-beer-bottle-30.png"))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("NƯỚC GIẢI KHÁT");

        javax.swing.GroupLayout pnlWaterLayout = new javax.swing.GroupLayout(pnlWater);
        pnlWater.setLayout(pnlWaterLayout);
        pnlWaterLayout.setHorizontalGroup(
            pnlWaterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWaterLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlWaterLayout.setVerticalGroup(
            pnlWaterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlFood.setForeground(new java.awt.Color(255, 255, 255));
        pnlFood.setOpaque(false);
        pnlFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlFoodMouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlFoodMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlFoodMouseEntered(evt);
            }
        });

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-food-30.png"))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("THỰC PHẨM");

        javax.swing.GroupLayout pnlFoodLayout = new javax.swing.GroupLayout(pnlFood);
        pnlFood.setLayout(pnlFoodLayout);
        pnlFoodLayout.setHorizontalGroup(
            pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFoodLayout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFoodLayout.setVerticalGroup(
            pnlFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlSouvenir.setForeground(new java.awt.Color(255, 255, 255));
        pnlSouvenir.setOpaque(false);
        pnlSouvenir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlSouvenirMouseReleased(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSouvenirMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSouvenirMouseEntered(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-wedding-gift-30.png"))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("ĐỒ LƯU NIỆM");

        javax.swing.GroupLayout pnlSouvenirLayout = new javax.swing.GroupLayout(pnlSouvenir);
        pnlSouvenir.setLayout(pnlSouvenirLayout);
        pnlSouvenirLayout.setHorizontalGroup(
            pnlSouvenirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSouvenirLayout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSouvenirLayout.setVerticalGroup(
            pnlSouvenirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout MenuLeftWareHouseLayout = new javax.swing.GroupLayout(MenuLeftWareHouse);
        MenuLeftWareHouse.setLayout(MenuLeftWareHouseLayout);
        MenuLeftWareHouseLayout.setHorizontalGroup(
            MenuLeftWareHouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlWater, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlFood, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlSouvenir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuLeftWareHouseLayout.setVerticalGroup(
            MenuLeftWareHouseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLeftWareHouseLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(pnlWater, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlSouvenir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        WarehouseContent.setBackground(new java.awt.Color(255, 255, 255));
        WarehouseContent.setLayout(new java.awt.CardLayout());

        SouvenirManagement.setBackground(new java.awt.Color(255, 255, 255));

        MenuTopWareHouse3.setkEndColor(new java.awt.Color(0, 204, 204));
        MenuTopWareHouse3.setkGradientFocus(200);
        MenuTopWareHouse3.setkStartColor(new java.awt.Color(87, 117, 223));

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("CẬP NHẬT HÀNG LƯU NIỆM");

        javax.swing.GroupLayout MenuTopWareHouse3Layout = new javax.swing.GroupLayout(MenuTopWareHouse3);
        MenuTopWareHouse3.setLayout(MenuTopWareHouse3Layout);
        MenuTopWareHouse3Layout.setHorizontalGroup(
            MenuTopWareHouse3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuTopWareHouse3Layout.setVerticalGroup(
            MenuTopWareHouse3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        moreItems2.setBackground(new java.awt.Color(255, 255, 255));
        moreItems2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Mới Hàng Hóa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(153, 51, 255))); // NOI18N

        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 102, 102));
        jLabel55.setText("Mặt Hàng");

        jLabel56.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 102, 102));
        jLabel56.setText("Loại Hàng");

        jLabel57.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 102, 102));
        jLabel57.setText("Giá");

        cbbMatHang2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbMatHang2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMatHang2.setBorder(null);
        cbbMatHang2.setOpaque(false);

        jTextField10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(0, 102, 102));
        jTextField10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 102)));
        jTextField10.setCaretColor(new java.awt.Color(0, 102, 102));

        jFormattedTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 102)));
        jFormattedTextField5.setForeground(new java.awt.Color(0, 102, 102));
        jFormattedTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField5.setCaretColor(new java.awt.Color(0, 102, 102));
        jFormattedTextField5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnLoginLogin4.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoginLogin4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-checkmark-40.png"))); // NOI18N
        btnLoginLogin4.setText("Cập Nhật");
        btnLoginLogin4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin4.setBorderPainted(false);
        btnLoginLogin4.setContentAreaFilled(false);

        btnLoginLogin5.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoginLogin5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-broom-40.png"))); // NOI18N
        btnLoginLogin5.setText("Nhập Lại");
        btnLoginLogin5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin5.setBorderPainted(false);
        btnLoginLogin5.setContentAreaFilled(false);

        javax.swing.GroupLayout moreItems2Layout = new javax.swing.GroupLayout(moreItems2);
        moreItems2.setLayout(moreItems2Layout);
        moreItems2Layout.setHorizontalGroup(
            moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moreItems2Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(moreItems2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnLoginLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoginLogin5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(moreItems2Layout.createSequentialGroup()
                        .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57))
                        .addGap(75, 75, 75)
                        .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbMatHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField10)
                                .addComponent(jFormattedTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(287, Short.MAX_VALUE))
        );
        moreItems2Layout.setVerticalGroup(
            moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moreItems2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel55)
                    .addComponent(cbbMatHang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(moreItems2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel56)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel57))
                    .addGroup(moreItems2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jFormattedTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(moreItems2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoginLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoginLogin5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout SouvenirManagementLayout = new javax.swing.GroupLayout(SouvenirManagement);
        SouvenirManagement.setLayout(SouvenirManagementLayout);
        SouvenirManagementLayout.setHorizontalGroup(
            SouvenirManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuTopWareHouse3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5)
            .addComponent(moreItems2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SouvenirManagementLayout.setVerticalGroup(
            SouvenirManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SouvenirManagementLayout.createSequentialGroup()
                .addComponent(MenuTopWareHouse3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moreItems2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        WarehouseContent.add(SouvenirManagement, "SouvenirManagement");

        FoodManagement.setBackground(new java.awt.Color(255, 255, 255));

        MenuTopWareHouse2.setkEndColor(new java.awt.Color(0, 204, 204));
        MenuTopWareHouse2.setkGradientFocus(200);
        MenuTopWareHouse2.setkStartColor(new java.awt.Color(87, 117, 223));

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("CẬP NHẬT THỰC PHẨM");

        javax.swing.GroupLayout MenuTopWareHouse2Layout = new javax.swing.GroupLayout(MenuTopWareHouse2);
        MenuTopWareHouse2.setLayout(MenuTopWareHouse2Layout);
        MenuTopWareHouse2Layout.setHorizontalGroup(
            MenuTopWareHouse2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuTopWareHouse2Layout.setVerticalGroup(
            MenuTopWareHouse2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        moreItems1.setBackground(new java.awt.Color(255, 255, 255));
        moreItems1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Mới Hàng Hóa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(153, 51, 255))); // NOI18N

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 102, 102));
        jLabel51.setText("Mặt Hàng");

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 102, 102));
        jLabel52.setText("Loại Hàng");

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 102, 102));
        jLabel53.setText("Giá");

        cbbMatHang1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbMatHang1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMatHang1.setBorder(null);
        cbbMatHang1.setOpaque(false);

        jTextField9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(0, 102, 102));
        jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 102)));
        jTextField9.setCaretColor(new java.awt.Color(0, 102, 102));

        jFormattedTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 102)));
        jFormattedTextField4.setForeground(new java.awt.Color(0, 102, 102));
        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField4.setCaretColor(new java.awt.Color(0, 102, 102));
        jFormattedTextField4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnLoginLogin2.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoginLogin2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-checkmark-40.png"))); // NOI18N
        btnLoginLogin2.setText("Cập Nhật");
        btnLoginLogin2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin2.setBorderPainted(false);
        btnLoginLogin2.setContentAreaFilled(false);

        btnLoginLogin3.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoginLogin3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-broom-40.png"))); // NOI18N
        btnLoginLogin3.setText("Nhập Lại");
        btnLoginLogin3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin3.setBorderPainted(false);
        btnLoginLogin3.setContentAreaFilled(false);

        javax.swing.GroupLayout moreItems1Layout = new javax.swing.GroupLayout(moreItems1);
        moreItems1.setLayout(moreItems1Layout);
        moreItems1Layout.setHorizontalGroup(
            moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moreItems1Layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(moreItems1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnLoginLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoginLogin3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(moreItems1Layout.createSequentialGroup()
                        .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53))
                        .addGap(75, 75, 75)
                        .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbMatHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField9)
                                .addComponent(jFormattedTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(287, Short.MAX_VALUE))
        );
        moreItems1Layout.setVerticalGroup(
            moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moreItems1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel51)
                    .addComponent(cbbMatHang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(moreItems1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel52)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel53))
                    .addGroup(moreItems1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(moreItems1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoginLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoginLogin3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout FoodManagementLayout = new javax.swing.GroupLayout(FoodManagement);
        FoodManagement.setLayout(FoodManagementLayout);
        FoodManagementLayout.setHorizontalGroup(
            FoodManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuTopWareHouse2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
            .addComponent(moreItems1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FoodManagementLayout.setVerticalGroup(
            FoodManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FoodManagementLayout.createSequentialGroup()
                .addComponent(MenuTopWareHouse2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moreItems1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        WarehouseContent.add(FoodManagement, "FoodManagement");

        WaterManagement.setBackground(new java.awt.Color(255, 255, 255));

        MenuTopWareHouse1.setkEndColor(new java.awt.Color(0, 204, 204));
        MenuTopWareHouse1.setkGradientFocus(200);
        MenuTopWareHouse1.setkStartColor(new java.awt.Color(87, 117, 223));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("CẬP NHẬT NƯỚC GIẢI KHÁT, BIA , RƯỢU,....");

        javax.swing.GroupLayout MenuTopWareHouse1Layout = new javax.swing.GroupLayout(MenuTopWareHouse1);
        MenuTopWareHouse1.setLayout(MenuTopWareHouse1Layout);
        MenuTopWareHouse1Layout.setHorizontalGroup(
            MenuTopWareHouse1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuTopWareHouse1Layout.setVerticalGroup(
            MenuTopWareHouse1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        moreItems.setBackground(new java.awt.Color(255, 255, 255));
        moreItems.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm Mới Hàng Hóa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(153, 51, 255))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 102, 102));
        jLabel41.setText("Mặt Hàng");

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 102, 102));
        jLabel48.setText("Loại Hàng");

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 102, 102));
        jLabel49.setText("Giá");

        cbbMatHang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbbMatHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMatHang.setBorder(null);
        cbbMatHang.setOpaque(false);

        jTextField7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 102));
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 102)));
        jTextField7.setCaretColor(new java.awt.Color(0, 102, 102));

        jFormattedTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 102)));
        jFormattedTextField3.setForeground(new java.awt.Color(0, 102, 102));
        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField3.setCaretColor(new java.awt.Color(0, 102, 102));
        jFormattedTextField3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnLoginLogin.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoginLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-checkmark-40.png"))); // NOI18N
        btnLoginLogin.setText("Cập Nhật");
        btnLoginLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin.setBorderPainted(false);
        btnLoginLogin.setContentAreaFilled(false);

        btnLoginLogin1.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoginLogin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images2/icons8-broom-40.png"))); // NOI18N
        btnLoginLogin1.setText("Nhập Lại");
        btnLoginLogin1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin1.setBorderPainted(false);
        btnLoginLogin1.setContentAreaFilled(false);

        javax.swing.GroupLayout moreItemsLayout = new javax.swing.GroupLayout(moreItems);
        moreItems.setLayout(moreItemsLayout);
        moreItemsLayout.setHorizontalGroup(
            moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moreItemsLayout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(moreItemsLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnLoginLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLoginLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(moreItemsLayout.createSequentialGroup()
                        .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49))
                        .addGap(75, 75, 75)
                        .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField7)
                                .addComponent(jFormattedTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        moreItemsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLoginLogin, btnLoginLogin1});

        moreItemsLayout.setVerticalGroup(
            moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moreItemsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41)
                    .addComponent(cbbMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(moreItemsLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel48)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel49))
                    .addGroup(moreItemsLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(moreItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoginLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoginLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout WaterManagementLayout = new javax.swing.GroupLayout(WaterManagement);
        WaterManagement.setLayout(WaterManagementLayout);
        WaterManagementLayout.setHorizontalGroup(
            WaterManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuTopWareHouse1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
            .addComponent(moreItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        WaterManagementLayout.setVerticalGroup(
            WaterManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WaterManagementLayout.createSequentialGroup()
                .addComponent(MenuTopWareHouse1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moreItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        WarehouseContent.add(WaterManagement, "WaterManagement");

        javax.swing.GroupLayout warehouseManagementLayout = new javax.swing.GroupLayout(warehouseManagement);
        warehouseManagement.setLayout(warehouseManagementLayout);
        warehouseManagementLayout.setHorizontalGroup(
            warehouseManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warehouseManagementLayout.createSequentialGroup()
                .addGroup(warehouseManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(warehouseManagementLayout.createSequentialGroup()
                        .addComponent(MenuLeftWareHouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(WarehouseContent, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(MenuTopWareHouse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        warehouseManagementLayout.setVerticalGroup(
            warehouseManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(warehouseManagementLayout.createSequentialGroup()
                .addComponent(MenuTopWareHouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(warehouseManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MenuLeftWareHouse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(warehouseManagementLayout.createSequentialGroup()
                        .addComponent(WarehouseContent, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 111, Short.MAX_VALUE))))
        );

        containPanel.add(warehouseManagement, "warehouseManagement");

        javax.swing.GroupLayout jpnMainLayout = new javax.swing.GroupLayout(jpnMain);
        jpnMain.setLayout(jpnMainLayout);
        jpnMainLayout.setHorizontalGroup(
            jpnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMainLayout.createSequentialGroup()
                .addComponent(LeftMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(containPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addComponent(TopMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1351, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpnMainLayout.setVerticalGroup(
            jpnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMainLayout.createSequentialGroup()
                .addComponent(TopMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LeftMenuPanel)
                    .addComponent(containPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE))
                .addGap(254, 254, 254))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1249, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeWindowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeWindowMouseReleased
//        System.exit(0);
    }//GEN-LAST:event_closeWindowMouseReleased

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
//        setColor(jPanel1);
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));

    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
//        jPanel1.setOpaque(false);
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));

    }//GEN-LAST:event_jPanel1MouseExited

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        jLabel9.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_jPanel2MouseExited

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        jLabel11.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_jPanel3MouseExited

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        jLabel13.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        jLabel15.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_jPanel5MouseExited

    private void closeWindowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeWindowMousePressed
        System.exit(0);
    }//GEN-LAST:event_closeWindowMousePressed


    private void minimizerWindowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizerWindowMousePressed
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizerWindowMousePressed

    static boolean maximized = true;
    private void maximizerWindowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maximizerWindowMousePressed
        if (maximized) {
            //handle fullscreen - taskbar
            JFrameMain.this.setExtendedState(JFrameMain.MAXIMIZED_BOTH);
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            JFrameMain.this.setMaximizedBounds(env.getMaximumWindowBounds());

            maximized = false;
        } else {

            setExtendedState(JFrameMain.NORMAL);

            maximized = true;
        }
    }//GEN-LAST:event_maximizerWindowMousePressed

    private void jpnMainMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnMainMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - mouseX, y - mouseY);
    }//GEN-LAST:event_jpnMainMouseDragged


    private void jpnMainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnMainMouseReleased
        setOpacity((float) 1.0);
    }//GEN-LAST:event_jpnMainMouseReleased

    private void jpnMainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpnMainMousePressed
        setOpacity((float) 0.5);
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_jpnMainMousePressed

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        CardLayout layout = (CardLayout) containPanel.getLayout();
        layout.show(containPanel, "roomManagement");
    }//GEN-LAST:event_jPanel1MouseReleased

    private void jPanel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseReleased
        CardLayout layout = (CardLayout) containPanel.getLayout();
        layout.show(containPanel, "personManagement");
    }//GEN-LAST:event_jPanel2MouseReleased

    private void jPanel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseReleased
        CardLayout layout = (CardLayout) containPanel.getLayout();
        layout.show(containPanel, "warehouseManagement");
    }//GEN-LAST:event_jPanel3MouseReleased

    private void jPanel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseReleased
        CardLayout layout = (CardLayout) containPanel.getLayout();
        layout.show(containPanel, "Statistical");
    }//GEN-LAST:event_jPanel4MouseReleased

    private void jPanel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseReleased
        CardLayout layout = (CardLayout) containPanel.getLayout();
        layout.show(containPanel, "pnlSetting");
    }//GEN-LAST:event_jPanel5MouseReleased

    private void jFormattedTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField13ActionPerformed

    private void btnOptionAccountMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionAccountMouseReleased
        CardLayout layout = (CardLayout) pnlOption.getLayout();
        layout.show(pnlOption, "pnlAccount");
    }//GEN-LAST:event_btnOptionAccountMouseReleased

    private void btnOptionAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionAccountActionPerformed


    }//GEN-LAST:event_btnOptionAccountActionPerformed

    private void btnOptionUserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUserMouseReleased
        CardLayout layout = (CardLayout) pnlOption.getLayout();
        layout.show(pnlOption, "pnlUser");
    }//GEN-LAST:event_btnOptionUserMouseReleased

    private void btnOptionUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUserActionPerformed

    private void btnOptionUser1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUser1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser1MouseReleased

    private void btnOptionUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser1ActionPerformed

    private void btnOptionUser2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUser2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser2MouseReleased

    private void btnOptionUser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUser2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser2ActionPerformed

    private void btnOptionUser3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUser3MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser3MouseReleased

    private void btnOptionUser3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUser3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser3ActionPerformed

    private void btnOptionUser4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUser4MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser4MouseReleased

    private void btnOptionUser4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUser4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser4ActionPerformed

    private void btnOptionUser5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUser5MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser5MouseReleased

    private void btnOptionUser5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUser5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser5ActionPerformed

    private void btnOptionUser6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUser6MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser6MouseReleased

    private void btnOptionUser6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionUser6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOptionUser6ActionPerformed

    private void btnOptionAccountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionAccountMouseEntered
        btnOptionAccount.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnOptionAccountMouseEntered

    private void btnOptionUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUserMouseEntered
        btnOptionUser.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnOptionUserMouseEntered

    private void btnOptionAccountMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionAccountMouseExited
        btnOptionAccount.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnOptionAccountMouseExited

    private void btnOptionUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOptionUserMouseExited
        btnOptionUser.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnOptionUserMouseExited

    private void pnlWaterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWaterMouseEntered
        jLabel31.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_pnlWaterMouseEntered

    private void pnlWaterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWaterMouseExited
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_pnlWaterMouseExited

    private void pnlWaterMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWaterMouseReleased
        CardLayout layout = (CardLayout) WarehouseContent.getLayout();
        layout.show(WarehouseContent, "WaterManagement");
    }//GEN-LAST:event_pnlWaterMouseReleased

    private void pnlFoodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFoodMouseEntered
        jLabel36.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_pnlFoodMouseEntered

    private void pnlFoodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFoodMouseExited
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_pnlFoodMouseExited

    private void pnlFoodMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFoodMouseReleased
        CardLayout layout = (CardLayout) WarehouseContent.getLayout();
        layout.show(WarehouseContent, "FoodManagement");
    }//GEN-LAST:event_pnlFoodMouseReleased

    private void pnlSouvenirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouvenirMouseEntered
        jLabel38.setForeground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_pnlSouvenirMouseEntered

    private void pnlSouvenirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouvenirMouseExited
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_pnlSouvenirMouseExited

    private void pnlSouvenirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouvenirMouseReleased
        CardLayout layout = (CardLayout) WarehouseContent.getLayout();
        layout.show(WarehouseContent, "SouvenirManagement");
    }//GEN-LAST:event_pnlSouvenirMouseReleased

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed
    private Border lineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
    private void jcbGiaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbGiaPhongActionPerformed
        model.Phong.GiaPhong();
    }//GEN-LAST:event_jcbGiaPhongActionPerformed

    private void lbPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbPhongMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbPhongMouseClicked

    private void jtxtNgayDiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNgayDiActionPerformed

    }//GEN-LAST:event_jtxtNgayDiActionPerformed

    private void btnNewUserClearMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewUserClearMouseReleased
        txtNewUserAddUser.setText("");
        txtNewUserAddPass.setText("");
        txtNewUserAddUser.requestFocus();
    }//GEN-LAST:event_btnNewUserClearMouseReleased

    private void txtNewUserAddPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewUserAddPassActionPerformed
        btnNewUserAddUserActionPerformed(evt);
    }//GEN-LAST:event_txtNewUserAddPassActionPerformed

    private void btnNewUserAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewUserAddUserActionPerformed
        if (btnNewUserAddUser.isEnabled()) {
            Remote lookup = null;

            try {
                lookup = Naming.lookup("rmi://localhost:1099/signin");
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(loginScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

            MySignIn myremote = (MySignIn) lookup;

            try {
                myremote.newUser(cbbNewUser.getSelectedItem().toString(), txtNewUserAddUser.getText().trim(), txtNewUserAddPass.getText().trim());
                refreshUsers();
                btnNewUserClearMouseReleased(null);
                txtNewUserAddUser.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    }//GEN-LAST:event_btnNewUserAddUserActionPerformed

//    public void setColor(JPanel panel) {
//        panel.setForeground(new java.awt.Color(151, 2, 254));
//        
//    }
//
//    public void resetColor(JPanel panel) {
//        panel.setOpaque(false);
//
//    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameMain().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cash1;
    private javax.swing.JPanel CustomerID1;
    private javax.swing.JPanel FoodManagement;
    private javax.swing.JLayeredPane LeftMenuPanel;
    private keeptoo.KGradientPanel MenuLeftWareHouse;
    private keeptoo.KGradientPanel MenuTopWareHouse;
    private keeptoo.KGradientPanel MenuTopWareHouse1;
    private keeptoo.KGradientPanel MenuTopWareHouse2;
    private keeptoo.KGradientPanel MenuTopWareHouse3;
    private static javax.swing.JPanel RoomStatus;
    private javax.swing.JPanel SignUpCustomer1;
    private javax.swing.JPanel SouvenirManagement;
    private keeptoo.KGradientPanel TopMenuPanel;
    private javax.swing.JPanel WarehouseContent;
    private javax.swing.JPanel WaterManagement;
    private javax.swing.JButton btnLoginLogin;
    private javax.swing.JButton btnLoginLogin1;
    private javax.swing.JButton btnLoginLogin2;
    private javax.swing.JButton btnLoginLogin3;
    private javax.swing.JButton btnLoginLogin4;
    private javax.swing.JButton btnLoginLogin5;
    private javax.swing.JButton btnNewAccAddAcc;
    private javax.swing.JButton btnNewAccClear;
    private javax.swing.JButton btnNewUserAddUser;
    private javax.swing.JButton btnNewUserClear;
    private javax.swing.JButton btnOptionAccount;
    private javax.swing.JButton btnOptionUser;
    private javax.swing.JButton btnOptionUser1;
    private javax.swing.JButton btnOptionUser2;
    private javax.swing.JButton btnOptionUser3;
    private javax.swing.JButton btnOptionUser4;
    private javax.swing.JButton btnOptionUser5;
    private javax.swing.JButton btnOptionUser6;
    private javax.swing.JComboBox<String> cbbMatHang;
    private javax.swing.JComboBox<String> cbbMatHang1;
    private javax.swing.JComboBox<String> cbbMatHang2;
    public static javax.swing.JComboBox<String> cbbNewUser;
    private javax.swing.JLabel closeWindow;
    private javax.swing.JPanel containPanel;
    private javax.swing.JLabel diachi;
    private javax.swing.JLabel image;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField14;
    private javax.swing.JFormattedTextField jFormattedTextField15;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField17;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField21;
    private javax.swing.JFormattedTextField jFormattedTextField22;
    private javax.swing.JFormattedTextField jFormattedTextField23;
    private javax.swing.JFormattedTextField jFormattedTextField24;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private static javax.swing.JPanel jPanel30;
    private static javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private static javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    public static javax.swing.JComboBox<String> jcbGiaPhong;
    private javax.swing.JComboBox<String> jcbTenNhanVien;
    private static javax.swing.JPanel jpnMain;
    private javax.swing.JFormattedTextField jtxtDatTruoc;
    private javax.swing.JFormattedTextField jtxtNgayDi;
    public static javax.swing.JFormattedTextField jtxtTienPhong;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    public static javax.swing.JLabel lbPhong;
    private javax.swing.JLabel maximizerWindow;
    private javax.swing.JLabel minimizerWindow;
    private javax.swing.JPanel moreItems;
    private javax.swing.JPanel moreItems1;
    private javax.swing.JPanel moreItems2;
    private javax.swing.JPanel personManagement;
    private javax.swing.JPanel pnlAccount;
    private javax.swing.JPanel pnlFood;
    private javax.swing.JPanel pnlNewAcc;
    private javax.swing.JPanel pnlNewUser;
    private javax.swing.JPanel pnlOption;
    private keeptoo.KGradientPanel pnlSetting;
    private javax.swing.JPanel pnlSouvenir;
    private javax.swing.JPanel pnlUser;
    private javax.swing.JPanel pnlWater;
    private javax.swing.JLabel programName;
    private javax.swing.JPanel roomManagement;
    public static javax.swing.JTable tblEmpList;
    public static javax.swing.JTable tblUserList;
    private javax.swing.JPasswordField txtNewUserAddPass;
    private javax.swing.JTextField txtNewUserAddUser;
    private javax.swing.JPanel warehouseManagement;
    // End of variables declaration//GEN-END:variables
}
