package hotelmanagenmentseverrmi;

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
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException {

        try {
            SignIn signin = new SignIn();
            Room room = new Room();
            CustomerSever customer = new CustomerSever();
            LocateRegistry.createRegistry(1099);
            GetAllUsers users = new GetAllUsers();
            GetAllEmployee emp = new GetAllEmployee();
            GetOnlyID empID = new GetOnlyID();
            Naming.rebind("rmi://localhost/emp", emp);
            Naming.rebind("rmi://localhost/users", users);
            Naming.rebind("rmi://localhost/empID", empID);
            Naming.rebind("rmi://localhost/signin", signin);
            Naming.rebind("rmi://localhost/room", room);
            Naming.rebind("rmi://localhost/customer", customer);
            System.out.println("Dang cho client gọi ham dang nhap:");
        } catch (RemoteException | MalformedURLException ex) {
            java.util.logging.Logger.getLogger(HotelManagenmentSeverRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
