/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementinterfacermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Trung
 */
public interface CheckGetAllEmpoyee extends Remote{
    public Vector GetAllEmpoyee() throws SQLException, ClassNotFoundException, RemoteException; 
}
