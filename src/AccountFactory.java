import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;

/**
 * The Account Factory - that will be used remotely that will be Serializable
 * and that will implement the Account Interface
 * @author Rory Nee - A00190040
 *
 */
public class AccountFactory extends UnicastRemoteObject implements AccountInterface,Serializable{

	/**
	 * Used 'serialVersionUID' because serialVersionUID is used to ensure that during deserialization 
	 *  the same class (that was used during serialize process) is loaded.
	 */
	private static final long serialVersionUID = -8479767892506957644L;
	// Declaration 
	private static AccountFactory theInstance = null;
	private Vector<Account> theListOfAccounts = new Vector<Account>();
	private DecimalFormat df = new DecimalFormat("\u20AC ##,###,##0.00"); // format the balance output

	/**
	 * A private constructor to create a Account Factory instance.
	 * So only one object can be made
	 * @throws RemoteException
	 */
	private AccountFactory() throws RemoteException {
		System.out.println("Constructor for factory has been called");
		onStart();
	}
	/**
	 * A method to get an instance of the account factory
	 * @return - AccountFactory 
	 * @throws RemoteException
	 */
	public static AccountFactory getInstance() throws RemoteException {
		
		if (theInstance == null) {
			theInstance = new AccountFactory();
			return theInstance;
		} else {
			System.out.println("The factory does already exist");
			return theInstance;
		}
	}
	/**
	 * A method to load the saved account information form file
	 */
	public void onStart() {
		
		try {
			FileInputStream aFileInStream = new FileInputStream("bankOfRorkBackUp.ser");

			ObjectInputStream aObjectInStream = new ObjectInputStream(aFileInStream);

			while(true){
				try{
					Account	savedAccounts = (Account) aObjectInStream.readObject();
					theListOfAccounts.add(savedAccounts);
				}catch(EOFException e){
					aObjectInStream.close();
					break;
				}
			}
			System.out.println("The object references has been read from the back-up file");

		} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"There Was A Problem Reading The Data from File.\n"
								+ "\nPlease call the IT Department", "System Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				System.exit(0); // Exit the program
		}
	}
	/**
	 * A method to Save the contents of the list of account to the file
	 */
	public void onSave() {
		
		try {
			FileOutputStream out = new FileOutputStream("bankOfRorkBackUp.ser");
			ObjectOutputStream oos = new ObjectOutputStream(out);
			
			for (Object a : theListOfAccounts) {

				Account acc = (Account) a;

				oos.writeObject(acc);
			}
			oos.close();
		} catch (Exception e1) {
			
			JOptionPane.showMessageDialog(null,"There Was A Problem backing up the accounts.\n" +
					"\nPlease call the IT Department","System Error",JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

	}

	/**
	 * A method to create an account.
	 * 
	 * @param aANum - Account Number
	 * @param aFName - First Name
	 * @param aLName - Second Name
	 * @param aAge - Age
	 * @param aSName - Street Name
	 * @param aTCity - Town/City Name
	 * @param aRegion - Region Name
	 * @param aCountry - Country Name
	 * @param aBalance - Initial Balance
	 */
	public void createAccount(int aANum, String aFName, String aLName,int aAge, String aSName,
			String aTCity, String aRegion, String aCountry, double aBalance) {
		System.out.println("Creating an Account called " + aANum);
		Account aAccount = new Account(aANum,aFName,aLName,aAge,aSName,aTCity,aRegion,aCountry,aBalance);
		theListOfAccounts.add(aAccount);
		onSave(); //Save the updated list of Accounts
	}
	/**
	 * A method to return a full list of account as and array list
	 * @return - ArrayList<String>
	 */
	public ArrayList<String> getAllAccounts(){
		
		ArrayList<String> accountList = new ArrayList<String>();
		// Sort the Vector list of accounts
		Collections.sort(theListOfAccounts); 
		
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			
			accountList.add(""+acc.getAccountNum());
			accountList.add(acc.getFirstName());
			accountList.add(acc.getLastName());
			accountList.add(""+acc.getAge());
			accountList.add(acc.getStreetName());
			accountList.add(acc.getTownCity());
			accountList.add(acc.getRegion());
			accountList.add(acc.getCountry());
			accountList.add(df.format(acc.getBalance())); // format the balance output
			
		}
		return accountList;
	}
	/**
	 * A method to return a particular account form the list of account as and array list
	 * @param - num - Account number to search for.
	 * @return - ArrayList<String>
	 */
	public ArrayList<String> getAccountById(int num){
		
		ArrayList<String> accountList = new ArrayList<String>(); 
		
		// iterate through the vector
		for(Object a : theListOfAccounts){
			Account acc = (Account) a;
			// Check to see if the entered number is the same as the present number
			if(num == acc.getAccountNum()) 
			{	// if it is return the account
				accountList.add(""+acc.getAccountNum());
				accountList.add(acc.getFirstName());
				accountList.add(acc.getLastName());
				accountList.add(""+acc.getAge());
				accountList.add(acc.getStreetName());
				accountList.add(acc.getTownCity());
				accountList.add(acc.getRegion());
				accountList.add(acc.getCountry());
				accountList.add(df.format(acc.getBalance())); // format the balance output
			}
		}
		return accountList;
	}
	/**
	 * A method to check the accounts for a persons last name
	 * @param - value - text to search
	 * @param - field - field to search
	 * @return - ArrayList<String> - a list of accounts
	 */
	public ArrayList<String> getAccountByField(String value, String field){
		
		ArrayList<String> accountList = new ArrayList<String>(); 
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			
			if(value.equalsIgnoreCase(acc.getLastName()) && field.equals("Last Name"))
			{
				accountList.add(""+acc.getAccountNum());
				accountList.add(acc.getFirstName());
				accountList.add(acc.getLastName());
				accountList.add(""+acc.getAge());
				accountList.add(acc.getStreetName());
				accountList.add(acc.getTownCity());
				accountList.add(acc.getRegion());
				accountList.add(acc.getCountry());
				accountList.add(df.format(acc.getBalance())); // format the balance output
			}
		}
		return accountList;
	}
	/**
	 * A method delete an account and then save it
	 * @param - num - the account number to be deleted
	 * @return - Boolean - true(account deleted) or false(could not delete or not found)
	 */
	public boolean deleteAccount(int num){
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			
			// If account matches the account being searched for
			if(num == acc.getAccountNum())
			{
				theListOfAccounts.remove(a);
				onSave(); //Save the updated list of Accounts
				return true;
			}	
		}
		return false;
	}
	/**
	 * A method to deposit money in to an account and save the update of the account list
	 * @param - accNum - The account number
	 * @param - Amount to be deposited
	 */
	public void depositMoney(int accNum, double amount){
		
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			
			// If account matches the account being searched for
			if(accNum == acc.getAccountNum())
			{
				acc.deposit(amount);
				onSave(); //Save the updated list of Accounts
				
			}	
		}
	}
	/**
	 * A method to withdraw money from an account and save the update of the account list
	 * @param - accNum - The account number
	 * @param - Amount to be deposited
	 * @return - String -  success, insufficient, noaccount
	 */
	public String withdrawMoney(int accNum, double amount){
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			// If account matches the account being searched for
			if(accNum == acc.getAccountNum())
			{
				if(acc.withdraw(amount)){
					onSave(); //Save the updated list of Accounts
					return "success"; // true - success
				}else{
					return "insufficient"; // False - No enough money 
				}
			}	
		}
		return "noaccount"; //Account does not exist 
	}
	/**
	 * Check to see if an account number is already in the system.
	 * All also used to check if it is not in the system.
	 * Used by new account and updating accounts
	 * 
	 * @param - accNum - The account number
	 * @return - Boolean - true(Account number is not valid - present)
	 * 					   false(Account number is valid - not present)
	 */
	public boolean accountNumberCheck(int accNum){
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			
			if(accNum == acc.getAccountNum())
			{
				return false; // Account number is not valid - present
			}	
		}
		return true; // Account number is valid - not present 
		
	}
	/**
	 * A method to update the account information in the account list
	 * 
	 * @param accountNum - Account Number
	 * @param fName - First Name
	 * @param lName - Second Name
	 * @param age - Age
	 * @param sName - Street Name
	 * @param town - Town/City Name
	 * @param region - Region Name
	 * @param country - Country Name
	 * @param balance - Initial Balance
	 */
	public void updateAccount(int accountNum, String fName, String lName, int age, String sName, 
			String town, String region, String country, double balance){
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			// If account matches the account being searched for
			if(accountNum == acc.getAccountNum())
			{
				acc.setFirstName(fName);
				acc.setLastName(lName);
				acc.setAge(age);
				acc.setStreetName(sName);
				acc.setTownCity(town);
				acc.setRegion(region);
				acc.setCountry(country);
				acc.setBalance(balance);
				onSave(); //Save the updated list of Accounts
				break;
			}	
		}
	}
	/**
	 * A method to get the transaction of a given account used in the show all feature
	 * @param - num - The account number
	 * @return - Vector<Transactions> - returns a vector of accounts
	 */
	public Vector<Transactions> showAllTranactions(int num){
		
		Vector<Transactions> accountTrans = new Vector<Transactions>(); 
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			
			if(num == acc.getAccountNum())
			{
				accountTrans = acc.getTransactions();
			}
		}
		return accountTrans;
	}
	/**
	 * A method to list and find the statistics of one field vrs another
	 * 
	 * @param - statA - The field 
	 * @param - statB - Balance
	 * @return - Map<String,Double> - The data for the statistics
	 */
	public Map<String,Double> getStasAVStatB(String statA, String statB){
		
		// Need to use the 'ConcurrentHashMap' so i can add and search thought the hash map
		Map<String,Double> statMap = new ConcurrentHashMap<String,Double>();
		
		boolean firsttime = true;
		boolean notThere = true;
		
		//Region Vrs Balance
		if(statA.equalsIgnoreCase("Region")&&statB.equalsIgnoreCase("Balance"))
		{
			// iterate through the vector
			for(Object a : theListOfAccounts){
				
				Account acc = (Account) a;
				
				notThere = true;
				if(firsttime){
					// add the fields and balance all it to the list the first time
					statMap.put(acc.getRegion(), acc.getBalance());
					firsttime = false; // change first time to false
				}else{
					// create an iterator to search through the new list
					// to check for duplicates on the field  
					Iterator<Map.Entry<String,Double>> iter = statMap.entrySet().iterator();
					
					// loop through the tree
					while (iter.hasNext()) {
						// Take the entry from the iterator  
					    Map.Entry<String,Double> entry = iter.next();
					    
					    // check to see if the field is equal to a key
					    if(entry.getKey().equalsIgnoreCase(acc.getRegion()))
						{
					    	// if equal add the balance and update the balance of the key
					    	double balan = entry.getValue()+acc.getBalance();
							entry.setValue(balan);
							notThere = false;
						}
					}
					if(notThere) // if it is not already there add it to the list
					{
						statMap.put(acc.getRegion(), acc.getBalance());
					}
				}
			}
			
		}else
		{
			// Town Vrs Balance
			// iterate through the vector
			for(Object a : theListOfAccounts){
				Account acc = (Account) a;
				
				notThere = true;
				if(firsttime){
					// add the fields and balance all it to the list the first time
					statMap.put(acc.getTownCity(), acc.getBalance());
					firsttime = false; // change first time to false
				}else{
					// create an iterator to search through the new list
					// to check for duplicates on the field 
					Iterator<Map.Entry<String,Double>> iter = statMap.entrySet().iterator();
					// loop through the tree
					while (iter.hasNext()) {
						// Take the entry from the iterator 
					    Map.Entry<String,Double> entry = iter.next();
					    // check to see if the field is equal to a key
					    if(entry.getKey().equalsIgnoreCase(acc.getTownCity()))
						{
					    	// if equal add the balance and update the balance of the key
					    	double balan = entry.getValue()+acc.getBalance();
							entry.setValue(balan);
							notThere = false;
						}
					}
					if(notThere) // if it is not already there add it to the list
					{
						statMap.put(acc.getTownCity(), acc.getBalance());
					}
				}
			}
		}
		return statMap;
	}
	/**
	 * A method to list and find the statistics of the age of the clients
	 * similar to "getStasAVStatB()" but needed to return two integers
	 * @return - Map<Integer,Integer> - The data for the statistics
	 */
	public Map<Integer,Integer> getAgeOfClients(){
		
		Map<Integer,Integer> statAgeMap = new ConcurrentHashMap<Integer,Integer>();
		boolean firsttime = true;
		boolean notThere = true;
		
		// Age of Clients
		// iterate through the vector
		for(Object a : theListOfAccounts){
			Account acc = (Account) a;
			
			notThere = true;
			if(firsttime){
				// add the fields and balance all it to the list the first time
				statAgeMap.put(acc.getAge(), 1);
				firsttime = false; // change first time to false
			}else{
				// create an iterator to search through the new list
				// to check for duplicates on the field 
				Iterator<Map.Entry<Integer,Integer>> iter = statAgeMap.entrySet().iterator();
				while (iter.hasNext()) {
					// loop through the tree
				    Map.Entry<Integer,Integer> entry = iter.next();
				    // check to see if the field is equal to a key
				    if(entry.getKey() == acc.getAge())
					{
				    	// if equal add the balance and update the balance of the key
				    	int count = entry.getValue() + 1;
						entry.setValue(count);
						notThere = false;
					}
				}
				if(notThere)
				{
					statAgeMap.put(acc.getAge(), 1); // if it is not already there add it to the list
				}
			}
		}
		return statAgeMap;
	}
	/**
	 * This methods return an arrayList of dynamic Account Statistics.
	 * 
	 * @return - ArrayList<Double> - A Array List of Overall Account Statistics
	 */
	public ArrayList<Double> getAccountStatistics(){
		// declaration of variables needed
		ArrayList<Double> accountStats = new ArrayList<Double>(); 
		double count = 0;
		double totalMoney = 0;
		double averMoney = 0;
		// Initialisation of max and min account variables 
		double maxAccount = theListOfAccounts.firstElement().getBalance();
		double minAccount = theListOfAccounts.firstElement().getBalance();
		double totalAge = 0;
		double averAge = 0;
		// iterate through the vector
		for(Object a : theListOfAccounts){
			
			Account acc = (Account) a;
			count++; // count the amount of accounts
			totalMoney +=acc.getBalance(); // Add the total balances
			totalAge += acc.getAge(); // Add the total ages
			if(maxAccount< acc.getBalance()) // Find the max Account
			{
				maxAccount = acc.getBalance();
			}
			if(minAccount > acc.getBalance()) // Find the smallest(Min) account
			{
				minAccount=acc.getBalance();
			}
			
		}
		averMoney = totalMoney/count; // Finds the average money in each account
		averAge = totalAge/count; // Finds the average age of the customers
		// Add them to the list.
		accountStats.add(totalMoney);
		accountStats.add(averMoney);
		accountStats.add(maxAccount);
		accountStats.add(minAccount);
		accountStats.add(averAge);
		accountStats.add(count);
		
		return accountStats;
	}
	
}
