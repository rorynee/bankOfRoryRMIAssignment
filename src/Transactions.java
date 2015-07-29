import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The Transaction class holds all the details of a Transaction of an account.
 * The Transaction class implements Serializable so the information can be saved to file
 * @author Rory Nee - A00190040
 */
public class Transactions implements Serializable {
	
	/**
	 * Used 'serialVersionUID' because serialVersionUID is used to ensure that during deserialization 
	 *  the same class (that was used during serialize process) is loaded.
	 */
	private static final long serialVersionUID = -3798520143258848268L;
	//Declarations - Set to private so they cannot be accessed directly
	private String timeStamp;
	private String trans;
	private String CBalance;
	private DecimalFormat df = new DecimalFormat("\u20AC ##,###,##0.00"); // format the balance output
	
	/**
	 * Transaction Constructor 
	 * @param transaction - The transaction made
	 * @param bal - Current Balance of the Account at time of transaction
	 */
	public Transactions(String transaction, double bal){
		timeStamp = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(Calendar.getInstance().getTime());
		trans = transaction;
		CBalance = df.format(bal);
	}
	/**
	 * A method to get the time stamp of the transaction
	 * @return - String - Time Stamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	/**
	 * A method to get the transaction
	 * @return - String - Transaction
	 */
	public String getTrans() {
		return trans;
	}
	/**
	 * A method to get the current balance at time of transaction 
	 * @return
	 */
	public String getCBalance() {
		return CBalance;
	}
	
}
