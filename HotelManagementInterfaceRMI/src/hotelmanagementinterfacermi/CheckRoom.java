/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementinterfacermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * @author Trung
 */
public interface CheckRoom extends Remote{
     public String checkRoom(String NameRoom) throws SQLException, ClassNotFoundException, RemoteException;
}
