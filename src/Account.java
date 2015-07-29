import java.text.DecimalFormat;
import java.util.Vector;
import java.io.Serializable;

/**
 * Account Class - Outlines all the information of a bank account
 * The Account Class implements Serializable so the information can be saved to file
 * and implements Comparable so the accounts can be sorted
 * @author Rory Nee - A00190040
 *
 */
public class Account implements Comparable<Account>, Serializable{
	
	/**
	 * Used 'serialVersionUID' because serialVersionUID is used to ensure that during deserialization 
	 *  the same class (that was used during serialize process) is loaded.
	 */
	private static final long serialVersionUID = -8485363330824926257L;
	// Declarations
	private int theAccountNum;
	private String theFirstName;
	private String theLastName;
	private int theAge;
	private String theStreetName;
	private String theTownCity;
	private String theRegion;
	private String theCountry;
	private double theBalance;
	private Vector<Transactions> myTrans;
	private DecimalFormat df = new DecimalFormat("\u20AC ##,###,##0.00"); // format the balance output
	
	/**
	 * The Constructor of the Account class
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
	public Account(int aANum, String aFName, String aLName,int aAge, String aSName,
			String aTCity, String aRegion, String aCountry, double aBalance) {
		super();
		this.theAccountNum = aANum;
		this.theFirstName = aFName;
		this.theLastName = aLName;
		this.theAge = aAge;
		this.theStreetName = aSName;
		this.theTownCity = aTCity;
		this.theRegion = aRegion;
		this.theCountry = aCountry;
		if(aBalance < 0) // Check to see if the balance is less then zero
		{
			this.theBalance = 0;
		}else
		{
			this.theBalance = aBalance;
		}
		myTrans = new Vector<Transactions>(); // Set up the Transactions
		// Add the creation of the account to the transactions
		myTrans.add(new Transactions("New Account Created",theBalance));
		
	}
	/**
	 * Get The Account Number
	 * @return - int - Account Number
	 */
	public int getAccountNum() {
		return theAccountNum;
	}
	/**
	 * Set the Account Number
	 * @param aAccountNum - New Account Number
	 */
	public void setAccountNum(int aAccountNum) {
		this.theAccountNum = aAccountNum;
	}
	/**
	 * Get The First Name
	 * @return - String - First Name
	 */
	public String getFirstName() {
		return theFirstName;
	}
	/**
	 * Set the First Name
	 * @param aAccountNum - New First Name
	 */
	public void setFirstName(String aFirstName) {
		this.theFirstName = aFirstName;
	}
	/**
	 * Get The Last Name
	 * @return - String - Last Name
	 */
	public String getLastName() {
		return theLastName;
	}
	/**
	 * Set the Last Name
	 * @param aAccountNum - New Last Name
	 */
	public void setLastName(String aLastName) {
		this.theLastName = aLastName;
	}
	/**
	 * Get The Age
	 * @return - int - Age
	 */
	public int getAge() {
		return theAge;
	}
	/**
	 * Set the Age
	 * @param aAccountNum - New Age
	 */
	public void setAge(int aAge) {
		this.theAge = aAge;
	}
	/**
	 * Get The Street Name
	 * @return - String - Street Name
	 */
	public String getStreetName() {
		return theStreetName;
	}
	/**
	 * Set the Street Name
	 * @param aAccountNum - New Street Name
	 */
	public void setStreetName(String aStreetName) {
		this.theStreetName = aStreetName;
	}
	/**
	 * Get The Town/City
	 * @return - String - Town/City
	 */
	public String getTownCity() {
		return theTownCity;
	}
	/**
	 * Set the Town/City
	 * @param aAccountNum - New Town/City
	 */
	public void setTownCity(String aTownCity) {
		this.theTownCity = aTownCity;
	}
	/**
	 * Get The Region
	 * @return - String - Region
	 */
	public String getRegion() {
		return theRegion;
	}
	/**
	 * Set the  Region
	 * @param aAccountNum - New  Region
	 */
	public void setRegion(String aRegion) {
		this.theRegion = aRegion;
	}
	/**
	 * Get The Country
	 * @return - String - Country
	 */
	public String getCountry() {
		return theCountry;
	}
	/**
	 * Set the Country
	 * @param aAccountNum - New Country
	 */
	public void setCountry(String aCountry) {
		this.theCountry = aCountry;
	}
	/**
	 * Get The Current Balance
	 * @return - double - Current Balance
	 */
	public double getBalance() {
		return theBalance;
	}
	/**
	 * Set the Balance
	 * @param aAccountNum - New Balance
	 */
	public void setBalance(double aBalance) {
		
		if(aBalance < 0)
		{
			this.theBalance = 0;
		}else
		{
			this.theBalance = aBalance;
		}
		
	}
	/**
	 * A method to Deposit money into the account
	 * @param amtD - Money to be deposited
	 */
	public void deposit(double amtD)
    {
		this.theBalance += amtD;
		// Add the transaction to the account transactions
		myTrans.add(new Transactions("Deposit made of "+df.format(amtD),this.theBalance));
    }
	/**
	 * A method to withdraw money from the account
	 * @param amtW
	 * @return - Boolean - True(Success) or False(Failure)
	 */
    public Boolean withdraw(double amtW)
    {
        if (amtW > this.theBalance) // Cannot take out more money than you have.
        {
            return false;
        }
        else
        {
        	this.theBalance -= amtW;
        	// Add the transaction to the account transactions
        	myTrans.add(new Transactions("Withdrawal made of "+df.format(amtW),this.theBalance));
            return true;
        }
    }
    /**
     * A Compare To Method to compare accounts by account number
     * 
     */
	@Override
	public int compareTo(Account other) {
		
		if(theAccountNum < other.getAccountNum())
		{
			return -1;
		}
		if(theAccountNum > other.getAccountNum())
		{
			return 1;
		}
		return 0;
	}
	/**
	 * A Method to get the transactions of the current account
	 * @return - Vector<Transactions> - The Current Account Transactions
	 */
	public Vector<Transactions> getTransactions(){

		return myTrans;
		
	}

}
