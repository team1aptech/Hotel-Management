/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementinterfacermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Trung
 */
public interface MySignIn extends Remote{
   public boolean checkPass(String Username, String Pass) throws SQLException, ClassNotFoundException,RemoteException;
   public void newUser(String MANHANVIEN, String Username, String Pass) throws SQLException, ClassNotFoundException,RemoteException;

    

}
