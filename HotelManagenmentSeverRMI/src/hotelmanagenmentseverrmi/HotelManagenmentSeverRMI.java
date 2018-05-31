/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagenmentseverrmi;

import com.sun.istack.internal.logging.Logger;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;

/**
 *
 * @author Trung
 */
public class HotelManagenmentSeverRMI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
      
        try {
            SignIn signin = new SignIn();
            Room room = new Room();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/signin", signin);
            Naming.rebind("rmi://localhost/room", room);
            System.out.println("Dang cho client gọi ham dang nhap:");
        } catch (RemoteException | MalformedURLException ex) {
            java.util.logging.Logger.getLogger(HotelManagenmentSeverRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
