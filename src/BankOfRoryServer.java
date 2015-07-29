import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * The bank of rory server
 * @author Rory Nee - A00190040
 */
public class BankOfRoryServer  {

	public static void main(String args[]){
		
	        try{
	        	System.out.println("Bank Of Rory Server Starting ....");
	        	// Get an instance of the Account Factory  
	        	AccountFactory aFactory = AccountFactory.getInstance();
	        	
	        	// Bind it to the string 'BankOfRoryServer' in the RMI Registry
	        	Naming.rebind("BankOfRoryServer", aFactory);
				
				System.out.println("RMI Server ready....");
				System.out.println("Waiting for Request...");
	            
	        }
	        catch(Exception e){
	        	System.out.println("Count not start the Bank Of Rory Server ....");
	            e.printStackTrace();
	        }
	    }
}
