/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author uphoto
 */
public class loginScreen extends javax.swing.JFrame {

    int mouseX;
    int mouseY;
    checkEmpty check = new checkEmpty();

    public loginScreen() {
        initComponents();
        loging.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.disabledBackground"));
        txtLoginUser.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.disabledBackground"));
        txtLoginPass.setBackground(javax.swing.UIManager.getDefaults().getColor("TextArea.disabledBackground"));
        check.start();
        
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        signin = new javax.swing.JPanel();
        loging = new javax.swing.JPanel();
        txtLoginUser = new javax.swing.JTextField();
        ldbLoginPass = new javax.swing.JLabel();
        lblLoginUser = new javax.swing.JLabel();
        txtLoginPass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        btnLoginLogin = new javax.swing.JButton();
        btnLoginExit = new javax.swing.JButton();
        loading = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        kGradientPanel1.setkEndColor(new java.awt.Color(0, 204, 204));
        kGradientPanel1.setkStartColor(new java.awt.Color(153, 0, 153));
        kGradientPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                kGradientPanel1MouseDragged(evt);
            }
        });
        kGradientPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                kGradientPanel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kGradientPanel1MouseReleased(evt);
            }
        });
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Sinhala MN", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText(" Aptech Hotel ");
        kGradientPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 330, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hotel-icon-10.png"))); // NOI18N
        jLabel5.setText("jLabel5");
        kGradientPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 360, 220));

        signin.setBackground(new java.awt.Color(14, 185, 199));
        signin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        signin.setOpaque(false);
        signin.setLayout(new java.awt.CardLayout());

        loging.setBackground(new java.awt.Color(36, 155, 192));
        loging.setOpaque(false);

        txtLoginUser.setBackground(new java.awt.Color(36, 155, 192));
        txtLoginUser.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        txtLoginUser.setForeground(new java.awt.Color(255, 255, 255));
        txtLoginUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLoginUser.setBorder(null);
        txtLoginUser.setCaretColor(new java.awt.Color(255, 255, 255));
        txtLoginUser.setOpaque(false);

        ldbLoginPass.setFont(new java.awt.Font("Sinhala MN", 1, 18)); // NOI18N
        ldbLoginPass.setForeground(new java.awt.Color(255, 255, 255));
        ldbLoginPass.setText("Password");

        lblLoginUser.setFont(new java.awt.Font("Sinhala MN", 1, 18)); // NOI18N
        lblLoginUser.setForeground(new java.awt.Color(255, 255, 255));
        lblLoginUser.setText("Username");

        txtLoginPass.setBackground(new java.awt.Color(153, 102, 255));
        txtLoginPass.setFont(new java.awt.Font("Sinhala MN", 1, 18)); // NOI18N
        txtLoginPass.setForeground(new java.awt.Color(255, 255, 255));
        txtLoginPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLoginPass.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtLoginPass.setCaretColor(new java.awt.Color(255, 255, 255));
        txtLoginPass.setOpaque(false);
        txtLoginPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginPassActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Sinhala MN", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sign In");

        btnLoginLogin.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-male-user-filled-30.png"))); // NOI18N
        btnLoginLogin.setText("Sign In");
        btnLoginLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLoginLogin.setBorderPainted(false);
        btnLoginLogin.setContentAreaFilled(false);
        btnLoginLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnLoginLoginMouseReleased(evt);
            }
        });
        btnLoginLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginLoginActionPerformed(evt);
            }
        });

        btnLoginExit.setBackground(new java.awt.Color(255, 255, 255));
        btnLoginExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-cancel-30.png"))); // NOI18N
        btnLoginExit.setText("Quit");
        btnLoginExit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLoginExit.setBorderPainted(false);
        btnLoginExit.setContentAreaFilled(false);
        btnLoginExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnLoginExitMouseReleased(evt);
            }
        });
        btnLoginExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout logingLayout = new javax.swing.GroupLayout(loging);
        loging.setLayout(logingLayout);
        logingLayout.setHorizontalGroup(
            logingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(logingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logingLayout.createSequentialGroup()
                        .addGroup(logingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(logingLayout.createSequentialGroup()
                                .addComponent(btnLoginLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLoginExit))
                            .addGroup(logingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblLoginUser)
                                .addComponent(ldbLoginPass)
                                .addComponent(txtLoginPass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45))
                    .addGroup(logingLayout.createSequentialGroup()
                        .addComponent(txtLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(logingLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        logingLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtLoginPass, txtLoginUser});

        logingLayout.setVerticalGroup(
            logingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logingLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(lblLoginUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ldbLoginPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLoginPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(logingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoginLogin)
                    .addComponent(btnLoginExit))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        logingLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtLoginPass, txtLoginUser});

        signin.add(loging, "card2");

        loading.setBackground(new java.awt.Color(255, 255, 255));
        loading.setOpaque(false);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lg.ring-loading-gif.gif"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Sinhala MN", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 51, 255));
        jLabel3.setText("Loggin in ...");

        javax.swing.GroupLayout loadingLayout = new javax.swing.GroupLayout(loading);
        loading.setLayout(loadingLayout);
        loadingLayout.setHorizontalGroup(
            loadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(loadingLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        loadingLayout.setVerticalGroup(
            loadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        signin.add(loading, "card3");

        kGradientPanel1.add(signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 300, 350));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void btnLoginLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginLoginActionPerformed
        loading.setVisible(true);
        loging.setVisible(false);

        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                loading.setVisible(false);
                loging.setVisible(true);
              if(btnLoginLogin.isEnabled()){
            try {
                if(User.checkPass(txtLoginUser.getText().trim(), txtLoginPass.getText().trim())){
                    new JFrameMain().setVisible(true);
                }else {
                    loging.setVisible(true);
                    loading.setVisible(false);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(loginScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

            }
        }, 1000 * 5);

    }//GEN-LAST:event_btnLoginLoginActionPerformed

    private void btnLoginLoginMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginLoginMouseReleased
       
    }//GEN-LAST:event_btnLoginLoginMouseReleased

    private void btnLoginExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginExitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginExitActionPerformed

    private void btnLoginExitMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginExitMouseReleased
        System.exit(0);
    }//GEN-LAST:event_btnLoginExitMouseReleased

    private void kGradientPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel1MousePressed
        setOpacity((float) 0.8);
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_kGradientPanel1MousePressed

    private void kGradientPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel1MouseReleased
        setOpacity((float) 1.0);
    }//GEN-LAST:event_kGradientPanel1MouseReleased

    private void kGradientPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel1MouseDragged

        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - mouseX, y - mouseY);
    }//GEN-LAST:event_kGradientPanel1MouseDragged

    private void txtLoginPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginPassActionPerformed
        btnLoginLoginActionPerformed(evt);
    }//GEN-LAST:event_txtLoginPassActionPerformed

    class checkEmpty extends Thread {

        @Override
        public void run() {
            System.out.println("Thread started");
            while (true) {
                try {
                    sleep(1);
                    if (txtLoginUser.getText().trim().isEmpty() || txtLoginPass.getText().trim().isEmpty()) {
                        btnLoginLogin.setEnabled(false);

                    } else {
                        btnLoginLogin.setEnabled(true);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(loginScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

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
            java.util.logging.Logger.getLogger(loginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoginExit;
    private javax.swing.JButton btnLoginLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lblLoginUser;
    private javax.swing.JLabel ldbLoginPass;
    private javax.swing.JPanel loading;
    private javax.swing.JPanel loging;
    private javax.swing.JPanel signin;
    private javax.swing.JPasswordField txtLoginPass;
    private javax.swing.JTextField txtLoginUser;
    // End of variables declaration//GEN-END:variables
}
