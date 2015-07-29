import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 * Account Interface that extends remote so it can be used with RMI
 * @author Rory Nee - A00190040
 */
public interface AccountInterface extends Remote
{
	// Declaration of all methods that are going to be used remotely by the MainGuiClass
	// and implemented by the Account Factory
	public void onStart() throws RemoteException;
	
	public void onSave()throws RemoteException;
 
	public void createAccount(int aANum, String aFName, String aLName,int aAge, String aSName,
			String aTCity, String aRegion, String aCountry, double aBalance)throws RemoteException;
	
	public ArrayList<String> getAllAccounts()throws RemoteException;
	
	public ArrayList<String> getAccountById(int num)throws RemoteException;
	
	public ArrayList<String> getAccountByField(String value, String field)throws RemoteException;

	public boolean deleteAccount(int num)throws RemoteException;

	public void depositMoney(int accNum, double amount)throws RemoteException;

	public String withdrawMoney(int accNum, double amount)throws RemoteException;

	public boolean accountNumberCheck(int accNum)throws RemoteException;
	
	public void updateAccount(int accountNum, String fName, String lName, int age, String sName, 
			String town, String region, String country, double balance)throws RemoteException;

	public Vector<Transactions> showAllTranactions(int num)throws RemoteException;
	
	public Map<String,Double> getStasAVStatB(String statA, String statB)throws RemoteException;

	public Map<Integer,Integer> getAgeOfClients()throws RemoteException;

	public ArrayList<Double> getAccountStatistics()throws RemoteException;

}
