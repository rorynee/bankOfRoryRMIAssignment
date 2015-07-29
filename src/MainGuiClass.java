import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * This Class sets up the main frame and adds panels, buttons and a drop down menu
 * All code is not commented as some code is repeated or is obvious as to what it does. 
 * @author Rory Nee - A00190040
 *
 */
public class MainGuiClass extends JFrame implements ActionListener
{

	/**
	 * Used 'serialVersionUID' because serialVersionUID is used to ensure that during deserialization 
	 *  the same class (that was used during serialize process) is loaded.
	 */
	private static final long serialVersionUID = 802033464398329699L;
	// Global Declaration so they can be used in other methods
	private static DefaultTableModel myModel;	// Default Table Model for JTable
	private JTable tableMain;			// JTable
	// Heading used in the table
	private static String[] listSetHeader = new String[] {"Account Number","First Name","Last Name","Age","Street Name","Town/City","Region","Country","Balance"};
	private static String[] transHeader = new String[] {"Date - Time","Recorded Transactions Information","Balance"};
	static String[][] table = null; // String Arrays used to print to the JTable

	private static final int FRAME_WIDTH = 1250; // width of the frame
	private static final int FRAME_HEIGHT = 700;	// height of the frame
	// Declaration of panels
	private JPanel radioPanel, middlePanel, outputPanel, optionsPanel,headPanel, logoPanel,newPanel; 
	private JPanel showallPanel,buttonPanel,eastPanel, depWitPanel,centerPanel, searchPanel, historyPanel, statsPanel;
	private ArrayList<JPanel> myPanels = new ArrayList<JPanel>(); // A List of panels
	
	private ButtonGroup buttonGroup1;  // Declaration of radio button group
	private JRadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6;  // Declaration of radio button
	private JRadioButton radioButton7, radioButton8, radioButton9;
	
	private JLabel listLabel, logoImage, logoLabel,showLabel, accountLabel, amountLabel, searchLabel;  // Declaration of labels
	private JLabel accountLabel_h, fnameLabel_h,lnameLabel_h, addressLabel_h,countyLabel_h, balanceLabel_h, statLabel;
	private JLabel accountLabel_n, fnameLabel_n, lnameLabel_n, ageLabel_n, streetLabel_n, townLabel_n, regionLabel_n, countyLabel_n, balanceLabel_n;   
	
	private JTextField accText_h, fText_h, lText_h, addText_h, countyryText_h, balText_h; // Declaration of JTextfields
	private JTextField accountText, amountText, searchText;
	private JTextField accText_n, fText_n, lText_n, ageText_n, streetText_n, townText_n, regionText_n, countyryText_n, balText_n;
	
	private JButton startButton, reSetButton, redoButton,redoStatButton, bExit;  // Declaration of buttons
	
	private ImageIcon icon = new ImageIcon(getClass().getResource("images\\icon.png")); // Declaration of the icon to be used
	// Format the decimal out put	
	private DecimalFormat df = new DecimalFormat("\u20AC ###,###,##0.00");
	private DecimalFormat dfTwo = new DecimalFormat("#0.00");
	// Declaration of the string arrays for the JComboBox
	private String[] searchChoice = {"Account Number","Last Name"}; // Drop down menus the search combo box
	private String[] statChoice = {"Region Vrs Balance","Town Vrs Balance","Age of Clients","Account Statistics"}; // Drop down menus the search combo box
	private JComboBox<String> searchOptions,statsOptions;
	
	private int buttonCheck = 1;	// this will be used to see which radio button is checked
	
	// Used by RMI to connect to the Factory
	private String theURL = "rmi:///";
	private AccountInterface theFactory;
	
	/**
	 * Main Gui Class - The main gui for the program.
	 * @param title
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public MainGuiClass(String title) throws MalformedURLException, RemoteException, NotBoundException
	{
		super(title);
		//Set the look and feel of the gui.
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		            defaults.put("Table.gridColor", new Color (214,217,223));
		            defaults.put("Table.disabled", false);
		            defaults.put("Table.showGrid", true);
		            defaults.put("Table.intercellSpacing", new Dimension(1, 1));
		            break;
		        }
		    }
		} catch (Exception e) {
		    // if Nimbus (present look and feel) is not available
			
	        	JOptionPane.showMessageDialog(null,"Couldn't Generate The Specified Look And Feel\n"+ 
						"Ging To Use The Default Look And Feel","System Error",JOptionPane.ERROR_MESSAGE);
		}
		// Look up the registry for the well known name 'BankOfRoryServer'
		theFactory  = (AccountInterface)Naming.lookup(theURL+"BankOfRoryServer"); 
		// Set up the icons
		setIconImage(icon.getImage()); // add the icon to the window
		setSize(FRAME_WIDTH, FRAME_HEIGHT); // set the size
		
		// Do nothing on close because i made my own version so i could save on close 
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// Add an event listener to the close(X) button on the window.
		WindowAdapter exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are You Sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0) {
                	// Try and save the file
					try {
						theFactory.onSave();
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Couldn't Generate Save To File\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
					System.exit(0); // Exit the program
                }
            }
        };
        addWindowListener(exitListener); // Add the listener
		
		// helper methods
		setUpMenu();	// set up the drop down menu
		setUpPanals();	// set up the panels to be used
		setUpLogo();	// Set up to Logo and the header label
		setUpMiddlePanel();	// set up the middle panel
		setUpRadioPanel();	// set up the panel with the radio buttons
		setUpOutputPanel();	// set up the main display output area
		
		myPanels.add(showallPanel); // Add the panels to an arraylist
		myPanels.add(depWitPanel);	// So later i will go through and see which i
		myPanels.add(searchPanel);	// need to make visable
		myPanels.add(historyPanel);
		myPanels.add(newPanel);
		myPanels.add(statsPanel);
		
		printAccounts(theFactory.getAllAccounts()); // Print the accounts to screen
		
		setVisible(true); // Show the frame
	}

	/**
	 * The method set up the main display area with a scrolling pane and a table inside it
	 * 
	 */
	private void setUpOutputPanel() 
	{
		// JTable 
		myModel = new DefaultTableModel(){
			@Override		// set the table so it is not editable
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}
		};
		tableMain = new JTable(myModel); // add the model to the table
		
		// Add a mouse listener to the table so it can be clickable.
		tableMain.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent evt) {
		        // Set up the row and column for the mouse click
				int row = tableMain.rowAtPoint(evt.getPoint());
				int col = 0; // zero because i only want the first row at first to be clickable
				// Find out which button is pressed so to know what kind of click action is desired
				if(buttonCheck == 2||buttonCheck == 3){
					// Deposit and withdraw screen
			        accountText.setText(tableMain.getValueAt(row, col).toString());
			        
		        }else if(buttonCheck == 8){
		        	// Delete screen
		        	searchText.setText(tableMain.getValueAt(row, col).toString());
		        	
		        }else if(buttonCheck == 5){
		        	// Search Screen - Search either by account number or by last name 
		        	col = tableMain.columnAtPoint(evt.getPoint());
		        	if(col == 2){ // last name column
		        		searchOptions.setSelectedIndex(1);
			        	searchText.setText(tableMain.getValueAt(row, 2).toString());
		        	}else{
		        		
		        		searchOptions.setSelectedIndex(0);
			        	searchText.setText(tableMain.getValueAt(row, 0).toString());
		        	}
		        	
		        }else if(buttonCheck == 4){
		        	// Show history
		        	accText_h.setText(tableMain.getValueAt(row, col).toString());
		        }else if(buttonCheck == 7){ 
		        	// Update
		        	accText_n.setText(tableMain.getValueAt(row, 0).toString());
					fText_n.setText(tableMain.getValueAt(row, 1).toString());
					lText_n.setText(tableMain.getValueAt(row, 2).toString());
					ageText_n.setText(tableMain.getValueAt(row, 3).toString());
					streetText_n.setText(tableMain.getValueAt(row, 4).toString());
					townText_n.setText(tableMain.getValueAt(row, 5).toString());
					regionText_n.setText(tableMain.getValueAt(row, 6).toString());
					countyryText_n.setText(tableMain.getValueAt(row, 7).toString());
					// The balance comes back as a string eg - € 34,000.00
					String bal = tableMain.getValueAt(row, 8).toString();
					String unformattedBal = bal.substring(2,bal.length()); // Take out the euro sign
					double unformatted = Double.valueOf((unformattedBal).replaceAll(",","")); // take out the commas
					 // set the text field to 34000.00 - valid when entering the update function 
					balText_n.setText(dfTwo.format(unformatted));
		        }
				
			}
		});
		tableMain.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // let the table resize its columns
		tableMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // only able to select one account
		JScrollPane jscrollTable = new JScrollPane(tableMain);	// add the table to the scrolling pane
		jscrollTable.getViewport().setBackground(Color.white);	// when no table is visible make the background white
		jscrollTable.setPreferredSize(new Dimension(1000,400));	// set the preferred size of the scrolling pane
		outputPanel.add(jscrollTable);	// add it to the panel
	}
	/**
	 * This method sets up the middle panel on the frame. 
	 * Some of the panels are used for more that one option
	 * 
	 */
	private void setUpMiddlePanel() 
	{
		// Set up a border for the panels
		Border etched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
		// west panel
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200,100));
		buttonPanel.setVisible(true);
		buttonPanel.setBorder(etched);
		
				// Add the label to the middle panel
				listLabel = new JLabel();
				listLabel.setPreferredSize(new Dimension(210,70));
				listLabel.setFont(new Font("Verdana", Font.BOLD, 20));
				listLabel.setBorder(new EmptyBorder(30, 20, 10, 0));
				buttonPanel.add(listLabel);
				middlePanel.add(buttonPanel, BorderLayout.WEST);
		
		// middle Panel
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(800,100));
		centerPanel.setVisible(true);
		middlePanel.add(centerPanel, BorderLayout.CENTER);
		
		// East Panel
		eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(200,100));
		eastPanel.setVisible(true);
		eastPanel.setBorder(etched);

				// Add the start Button to east
				startButton = new JButton();
				startButton.setPreferredSize(new Dimension(100,30));
				startButton.addActionListener(this);	// add the action listener
				eastPanel.add(startButton);
		
				// Add the reset button to east
				reSetButton = new JButton();
				reSetButton.setText("Reset All");
				reSetButton.setPreferredSize(new Dimension(100,30));
				reSetButton.addActionListener(this);	// add the action listener
				eastPanel.add(reSetButton);
		
		middlePanel.add(eastPanel, BorderLayout.EAST);
		
		// Centre panel
		/*
		 *  Show All Button
		 */
		showallPanel = new JPanel();
		showallPanel.setPreferredSize(new Dimension(800,100));
		showallPanel.setVisible(false);
		
		// Add the label to the panel
		showLabel = new JLabel("The Screen Below Shows The Current Account Holders");
		showLabel.setPreferredSize(new Dimension(500,30));
		showLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		showallPanel.add(showLabel);
		
		centerPanel.add(showallPanel, BorderLayout.CENTER); // Add the Panel
		/*
		 *  Deposit and Withdrawn
		 */
		depWitPanel = new JPanel();
		depWitPanel.setVisible(false);
		depWitPanel.setLayout(new GridLayout(3,2));
		depWitPanel.setPreferredSize(new Dimension(400,100));
		
		accountLabel = new JLabel("Enter Acount Number :");
		accountLabel.setPreferredSize(new Dimension(150,30));
		
		amountLabel = new JLabel("Enter Amount            €");
		amountLabel.setPreferredSize(new Dimension(150,30));
		
		accountText = new JTextField();
		accountText.setMargin(new Insets(0, 5, 0, 0)); // Add a space at the start of the text field
		accountText.setPreferredSize(new Dimension(100,30));
		
		
		amountText = new JTextField();
		amountText.addActionListener(this);
		amountText.setMargin(new Insets(0, 5, 0, 0));
		amountText.setPreferredSize(new Dimension(100,30));
		
		depWitPanel.add(accountLabel);
		depWitPanel.add(accountText);
		// Add a box filler between the components to add extra padding
		depWitPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(10,10),new Dimension(15,15)));
		depWitPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(10,10),new Dimension(15,15)));
		depWitPanel.add(amountLabel);
		depWitPanel.add(amountText);
		
		centerPanel.add(depWitPanel, BorderLayout.CENTER); // Add the Panel
		/*
		 * Search and Delete Option
		 */
		searchPanel = new JPanel();
		searchPanel.setVisible(false);
		searchPanel.setPreferredSize(new Dimension(800,100));
		
		searchLabel = new JLabel("Please Choose Search Options :");
		searchLabel.setPreferredSize(new Dimension(200,30));
		
		searchText = new JTextField();
		searchText.addActionListener(this);
		searchText.setMargin(new Insets(0, 5, 0, 0));
		searchText.setPreferredSize(new Dimension(100,30));
		
		searchOptions = new JComboBox<String>(searchChoice); // Add a comdo box i.e. drop down box
		searchOptions.setSelectedIndex(0);
		searchOptions.setPreferredSize(new Dimension(140,30));
		searchOptions.setEditable(false);
		
		redoButton= new JButton();
		redoButton.setText("Reset");
		redoButton.setPreferredSize(new Dimension(100,30));
		redoButton.addActionListener(this);	// add the action listener
	
		searchPanel.add(searchLabel);
		searchPanel.add(searchText);
		searchPanel.add(searchOptions);
		searchPanel.add(redoButton);
		
		centerPanel.add(searchPanel, BorderLayout.CENTER); // Add the Panel
		/*
		 * Show History
		 */
		historyPanel = new JPanel(new GridLayout(5,4));
		historyPanel.setVisible(false);
		historyPanel.setPreferredSize(new Dimension(700,120));
		
		accountLabel_h = new JLabel("Enter Account Number :");
		accountLabel_h.setPreferredSize(new Dimension(150,30));
		
		fnameLabel_h = new JLabel("First Name :");
		fnameLabel_h.setPreferredSize(new Dimension(150,30));
		
		lnameLabel_h = new JLabel("Last Name :");
		lnameLabel_h.setPreferredSize(new Dimension(150,30));
		
		addressLabel_h = new JLabel("  Street, Town :");
		addressLabel_h.setPreferredSize(new Dimension(150,30));
		
		countyLabel_h = new JLabel("  Region, Country :");
		countyLabel_h.setPreferredSize(new Dimension(150,30));
		
		balanceLabel_h = new JLabel("  Current Balance        €");
		balanceLabel_h.setPreferredSize(new Dimension(150,30));
		
		accText_h = new JTextField();
		accText_h.setMargin(new Insets(0, 5, 0, 0));
		accText_h.setPreferredSize(new Dimension(100,30));
		
		fText_h = new JTextField();
		fText_h.setMargin(new Insets(0, 5, 0, 0));
		fText_h.setPreferredSize(new Dimension(100,30));
		fText_h.setEditable(false);
		
		lText_h = new JTextField();
		lText_h.setMargin(new Insets(0, 5, 0, 0));
		lText_h.setPreferredSize(new Dimension(100,30));
		lText_h.setEditable(false);
		
		addText_h = new JTextField();
		addText_h.setMargin(new Insets(0, 5, 0, 0));
		addText_h.setPreferredSize(new Dimension(100,30));
		addText_h.setEditable(false);
		
		countyryText_h = new JTextField();
		countyryText_h.setMargin(new Insets(0, 5, 0, 0));
		countyryText_h.setPreferredSize(new Dimension(100,30));
		countyryText_h.setEditable(false);
		
		balText_h = new JTextField();
		balText_h.setMargin(new Insets(0, 5, 0, 0));
		balText_h.setPreferredSize(new Dimension(100,30));
		balText_h.setEditable(false);
		
		historyPanel.add(accountLabel_h);historyPanel.add(accText_h);historyPanel.add(addressLabel_h);historyPanel.add(addText_h);
		// padding between elements
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		
		historyPanel.add(fnameLabel_h);historyPanel.add(fText_h);historyPanel.add(countyLabel_h);historyPanel.add(countyryText_h);
		
		// padding between elements
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		historyPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		
		historyPanel.add(lnameLabel_h);historyPanel.add(lText_h);historyPanel.add(balanceLabel_h);historyPanel.add(balText_h);
		
		centerPanel.add(historyPanel, BorderLayout.CENTER); // Add the Panel
		/*
		 * New Account and Update Options
		 */
		newPanel = new JPanel(new GridLayout(5,6));
		newPanel.setVisible(false);
		newPanel.setPreferredSize(new Dimension(800,120));
		
		accountLabel_n = new JLabel("Account Number :");
		accountLabel_n.setPreferredSize(new Dimension(100,30));
		
		fnameLabel_n = new JLabel("First Name :");
		fnameLabel_n.setPreferredSize(new Dimension(100,30));
		
		lnameLabel_n = new JLabel("Last Name :");
		lnameLabel_n.setPreferredSize(new Dimension(100,30));
		
		ageLabel_n = new JLabel("  Age :");
		ageLabel_n.setPreferredSize(new Dimension(80,30));
		
		streetLabel_n = new JLabel("  Street :");
		streetLabel_n.setPreferredSize(new Dimension(80,30));
		
		townLabel_n = new JLabel("  Town :");
		townLabel_n.setPreferredSize(new Dimension(80,30));
		
		regionLabel_n = new JLabel("  Region :");
		regionLabel_n.setPreferredSize(new Dimension(80,30));
		
		countyLabel_n = new JLabel("  Country :");
		countyLabel_n.setPreferredSize(new Dimension(80,30));
		
		balanceLabel_n = new JLabel("  Inital Balance           €");
		balanceLabel_n.setPreferredSize(new Dimension(80,30));
		
		accText_n = new JTextField();
		accText_n.setMargin(new Insets(0, 5, 0, 0));
		accText_n.setPreferredSize(new Dimension(80,30));
		
		fText_n = new JTextField();
		fText_n.setMargin(new Insets(0, 5, 0, 0));
		fText_n.setPreferredSize(new Dimension(80,30));
		
		lText_n = new JTextField();
		lText_n.setMargin(new Insets(0, 5, 0, 0));
		lText_n.setPreferredSize(new Dimension(80,30));
		
		ageText_n = new JTextField();
		ageText_n.setMargin(new Insets(0, 5, 0, 0));
		ageText_n.setPreferredSize(new Dimension(80,30));
		
		streetText_n = new JTextField();
		streetText_n.setMargin(new Insets(0, 5, 0, 0));
		streetText_n.setPreferredSize(new Dimension(80,30));
		
		townText_n = new JTextField();
		townText_n.setMargin(new Insets(0, 5, 0, 0));
		townText_n.setPreferredSize(new Dimension(80,30));
		
		regionText_n = new JTextField();
		regionText_n.setMargin(new Insets(0, 5, 0, 0));
		regionText_n.setPreferredSize(new Dimension(80,30));
		
		countyryText_n = new JTextField();
		countyryText_n.setMargin(new Insets(0, 5, 0, 0));
		countyryText_n.setPreferredSize(new Dimension(80,30));
		
		balText_n = new JTextField();
		balText_n.setMargin(new Insets(0, 5, 0, 0));
		balText_n.setPreferredSize(new Dimension(80,30));
		
		newPanel.add(accountLabel_n);newPanel.add(accText_n);newPanel.add(ageLabel_n);newPanel.add(ageText_n);newPanel.add(regionLabel_n);newPanel.add(regionText_n);
		// padding between elements
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(fnameLabel_n);newPanel.add(fText_n);newPanel.add(streetLabel_n);newPanel.add(streetText_n);newPanel.add(countyLabel_n);newPanel.add(countyryText_n);
		// padding between elements
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
		newPanel.add(lnameLabel_n);newPanel.add(lText_n);newPanel.add(townLabel_n);newPanel.add(townText_n);newPanel.add(balanceLabel_n);newPanel.add(balText_n);
		
		centerPanel.add(newPanel, BorderLayout.CENTER); // Add the Panel
		/*
		 * Statistics
		 */
		statsPanel = new JPanel();
		statsPanel.setVisible(false);
		statsPanel.setPreferredSize(new Dimension(800,100));
		
		statLabel = new JLabel("Please Choose Statistics Options :");
		statLabel.setPreferredSize(new Dimension(200,30));
		
		statsOptions = new JComboBox<String>(statChoice); // Add the combo Box
		statsOptions.setSelectedIndex(0);
		statsOptions.setPreferredSize(new Dimension(170,30));
		statsOptions.setEditable(false);
		
		redoStatButton = new JButton();
		redoStatButton.setText("Reset");
		redoStatButton.setPreferredSize(new Dimension(100,30));
		redoStatButton.addActionListener(this);	// add the action listener
	
		statsPanel.add(statLabel);
		statsPanel.add(statsOptions);
		statsPanel.add(redoStatButton);
		
		centerPanel.add(statsPanel, BorderLayout.CENTER); // Add the Panel
	}
	/**
	 * This method sets up the main Logo and the Logo text on the screen
	 */
	private void setUpLogo(){
		// Add the long to a label
		logoImage = new JLabel();
		logoImage.setIcon(new ImageIcon(getClass().getResource("images\\borlogo.png")));
		logoImage.setPreferredSize(new Dimension(50,50));
		logoPanel.add(logoImage);
		// Add a Label saying - 'Bank of Rory'
		logoLabel = new JLabel("Bank of Rory");
		logoLabel.setFont(new Font("Georgia", Font.BOLD, 25));
		logoPanel.add(logoLabel);
		
	}
	
	/**
	 * This method sets up the radio button panel and also adds the logo panel
	 */
	private void setUpRadioPanel() 
	{

		// set up the radio button group
		buttonGroup1 = new ButtonGroup();
		// set up the radio button one
		radioButton1 = new JRadioButton();
		radioButton1.setText("Show All");	// set the text of the radio button
		
		radioButton1.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				myModel.setColumnCount(0);	// clear the tables rows and columns
				myModel.setRowCount(0);
				
					try {
						printAccounts(theFactory.getAllAccounts()); // call the getAll Accounts function
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				
				listLabel.setText("Show All");	// set the list label to "Show All"
				listLabel.setForeground(Color.RED); // Match the colouring for the show all option
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.red));
				outputPanel.setBackground(Color.RED); // match the colouring
				startButton.setText("Show All"); // change the text on the start button
				buttonCheck = 1;	// set button check to check what 'Reset all' option is needed
				showHide(showallPanel); // A method to show the current panel and hide all others
			}
		});  
		optionsPanel.add(radioButton1);	// add the radio button to the options panel
		buttonGroup1.add(radioButton1);	// add the radio button to the button group 

		radioButton2 = new JRadioButton();
		radioButton2.setText("Deposit");

		radioButton2.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("Deposit");
				startButton.setText("Deposit");
				listLabel.setForeground(Color.BLUE);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLUE));
				outputPanel.setBackground(Color.BLUE);
				showHide(depWitPanel);
				buttonCheck = 2;
				clearPanel(); // Clear the panels text box depending on buttonCheck 
			}
		}); 

		optionsPanel.add(radioButton2);
		buttonGroup1.add(radioButton2);

		radioButton3 = new JRadioButton();
		radioButton3.setText("Withdraw");

		radioButton3.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("Withdraw");
				startButton.setText("Withdraw");
				listLabel.setForeground(Color.BLACK);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
				outputPanel.setBackground(Color.BLACK);
				showHide(depWitPanel);
				buttonCheck = 3;
				clearPanel();
			}
		}); 
		optionsPanel.add(radioButton3);
		buttonGroup1.add(radioButton3);
		
		radioButton4 = new JRadioButton();
		radioButton4.setText("Show History");

		radioButton4.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("Show History");
				startButton.setText("History");
				listLabel.setForeground(Color.GREEN);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.GREEN));
				outputPanel.setBackground(Color.GREEN);
				showHide(historyPanel);
				buttonCheck = 4;
				clearPanel();
			}
		}); 
		optionsPanel.add(radioButton4);
		buttonGroup1.add(radioButton4);
		
		radioButton5 = new JRadioButton();
		radioButton5.setText("Search");

		radioButton5.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("Search");
				startButton.setText("Search");
				listLabel.setForeground(Color.YELLOW);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.YELLOW));
				outputPanel.setBackground(Color.YELLOW);
				searchOptions.setSelectedIndex(0); // Set up the combo value you want to show
				showHide(searchPanel);
				buttonCheck = 5;
				clearPanel();
				searchOptions.setVisible(true);
				searchLabel.setText("Please Choose Search Options :");
			}
		}); 
		optionsPanel.add(radioButton5);
		buttonGroup1.add(radioButton5);
		
		// Set up the starting radio button that is checked
		radioButton5.setSelected(true);
		listLabel.setText("Search");
		listLabel.setForeground(Color.YELLOW);
		centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.YELLOW));
		outputPanel.setBackground(Color.YELLOW);
		searchOptions.setSelectedIndex(0);
		searchOptions.setVisible(true);
		startButton.setText("Search");
		showHide(searchPanel);
		buttonCheck = 5;
		
		radioButton6 = new JRadioButton();
		radioButton6.setText("New Account");

		radioButton6.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("New Account");
				listLabel.setForeground(Color.LIGHT_GRAY);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
				outputPanel.setBackground(Color.LIGHT_GRAY);
				balanceLabel_n.setText("  Inital Balance            €");
				startButton.setText("New Account");
				buttonCheck = 6;
				showHide(newPanel);
				clearPanel();
			}
		}); 
		optionsPanel.add(radioButton6);
		buttonGroup1.add(radioButton6);
		
		radioButton7 = new JRadioButton();
		radioButton7.setText("Update Account");

		radioButton7.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("Update Account");
				listLabel.setForeground(Color.DARK_GRAY);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.DARK_GRAY));
				outputPanel.setBackground(Color.DARK_GRAY);
				balanceLabel_n.setText("  Current Balance        €");
				startButton.setText("Update");
				buttonCheck = 7;
				showHide(newPanel);
				clearPanel();
			}
		}); 
		optionsPanel.add(radioButton7);
		buttonGroup1.add(radioButton7);
		
		radioButton8 = new JRadioButton();
		radioButton8.setText("Delete Account");

		radioButton8.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				listLabel.setText("Delete Account");
				startButton.setText("Delete");
				listLabel.setForeground(Color.MAGENTA);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.MAGENTA));
				outputPanel.setBackground(Color.MAGENTA);
				buttonCheck = 8;
				showHide(searchPanel);
				clearPanel();
				searchLabel.setText("Enter Account Number :"); // Change the text around for the delete function
				searchOptions.setVisible(false); // No need for search options
			}
		}); 
		optionsPanel.add(radioButton8);
		buttonGroup1.add(radioButton8);
		
		radioButton9 = new JRadioButton();
		radioButton9.setText("Statistics ");

		radioButton9.addActionListener(new ActionListener() {// set up the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				
				listLabel.setText("Statistics");
				startButton.setText("Statistics");
				listLabel.setForeground(Color.PINK);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.PINK));
				outputPanel.setBackground(Color.PINK);
				buttonCheck = 9;
				statsOptions.setSelectedIndex(0);
				showHide(statsPanel);				
				clearPanel();
				
			}
		}); 
		optionsPanel.add(radioButton9);
		buttonGroup1.add(radioButton9);
	}
	/**
	 * This method set up the different panels and nested panels 
	 * 
	 */
	private void setUpPanals() 
	{
		// Set up the logo panel
		logoPanel = new JPanel();
		logoPanel.setLayout(new FlowLayout());
		logoPanel.setPreferredSize(new Dimension(300,60));

		// Set up the radio button panel
		radioPanel = new JPanel();
		radioPanel.setLayout(new BorderLayout());
		radioPanel.setVisible(true);
		
		// add the options panel to the radio panel - nested
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new FlowLayout()); // set to flowLayout
		optionsPanel.setVisible(true);
		radioPanel.add(optionsPanel, BorderLayout.CENTER);
		
		// Set up a gridbag layout for the head panel
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

		headPanel= new JPanel();
		headPanel.setVisible(true);
		headPanel.setLayout(gridbag);
		
        c.fill = GridBagConstraints.HORIZONTAL;
        
       
        c.gridx = 0;
        c.gridy = 0;
        gridbag.setConstraints(radioPanel, c);
        headPanel.add(radioPanel);
        
       
        c.gridx = 1;
        c.gridy = 0;
        gridbag.setConstraints(logoPanel, c);
        headPanel.add(logoPanel);
		
        getContentPane().add(headPanel, BorderLayout.NORTH);

		// add the middle panel
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.setPreferredSize(new Dimension(FRAME_WIDTH, 150));
		middlePanel.setVisible(true);
		getContentPane().add(middlePanel, BorderLayout.CENTER);

		// add the output panel
		outputPanel = new JPanel();
		outputPanel.setLayout(new FlowLayout());
		outputPanel.setVisible(true);

		getContentPane().add(outputPanel, BorderLayout.SOUTH);
		

	}
	/**
	 * This method sets up the drop down menu on the frame.
	 */
	private void setUpMenu()
	{
		// Set up The Menu Bar on frame
		JMenuBar menuBar = new JMenuBar(); // add the menu bar
		/*
		 *  File drop down options
		 */
		JMenu menu = new JMenu("File");    // create the menu
		menu.setMnemonic(KeyEvent.VK_F); // Set up Mnemonic 'alt' F
		menu.setPreferredSize(new Dimension(75,25));
		menuBar.add(menu);                 // add the menu to the menu bar		
		
		JMenuItem fileItemExit = new JMenuItem("Exit"); // create a menu item
		fileItemExit.setPreferredSize(new Dimension(72,25));
		fileItemExit.addActionListener(new ActionListener() { // add the action listener

			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Are You Sure you want to exit?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0) {
                	
						try {
							theFactory.onSave(); // Save on exit
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Generate Save To File\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
                	System.exit(0); // Exit the program
                }
			}
		});
		menu.add(fileItemExit);             //add the menu item
		/*
		 *  "Edit" drop down options
		 */
		JMenu menu2 = new JMenu("Edit");    // create the second menu option
		menu2.setMnemonic(KeyEvent.VK_E);
		menu2.setPreferredSize(new Dimension(100,25));
		menuBar.add(menu2);                 // add the menu to the menu bar
		JMenuItem fileItem6 = new JMenuItem("New Account"); // create a menu item
		fileItem6.setPreferredSize(new Dimension(97,25));
		fileItem6.addActionListener(new ActionListener() {

			// All menu should echo the option from the above matching radio buttons
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton6.setSelected(true);
				listLabel.setText("New Account");
				listLabel.setForeground(Color.LIGHT_GRAY);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
				outputPanel.setBackground(Color.LIGHT_GRAY);
				balanceLabel_n.setText("  Inital Balance           €");
				startButton.setText("New Account");
				buttonCheck = 6;
				showHide(newPanel);
				clearPanel();

			}
		});
		menu2.add(fileItem6);             //add the menu item
		
		menu2.add(new JSeparator());          // add a separator to the menu
		JMenuItem fileItem7 = new JMenuItem("Update"); // create a menu item
		fileItem7.setPreferredSize(new Dimension(97,25));
		fileItem7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton7.setSelected(true);
				listLabel.setText("Update Account");
				listLabel.setForeground(Color.DARK_GRAY);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.DARK_GRAY));
				outputPanel.setBackground(Color.DARK_GRAY);
				balanceLabel_n.setText("  Current Balance           €");
				startButton.setText("Update");
				buttonCheck = 7;
				showHide(newPanel);
				clearPanel();

			}
		});
		menu2.add(fileItem7);             //add the menu item
		
		menu2.add(new JSeparator());          // add a separator to the menu
		JMenuItem fileItem8 = new JMenuItem("Delete"); // create a menu item
		fileItem8.setPreferredSize(new Dimension(97,25));
		fileItem8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton8.setSelected(true);
				listLabel.setText("Delete Account");
				startButton.setText("Delete");
				listLabel.setForeground(Color.MAGENTA);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.MAGENTA));
				outputPanel.setBackground(Color.MAGENTA);
				buttonCheck = 8;
				showHide(searchPanel);
				clearPanel();
				searchLabel.setText("Enter Account Number :");
				searchOptions.setVisible(false); 

			}
		});
		menu2.add(fileItem8);             //add the menu item
		/* 
		 * "View" drop down options
		 */
		JMenu menu3 = new JMenu("View");    // create the third menu option
		menu3.setMnemonic(KeyEvent.VK_V);
		menu3.setPreferredSize(new Dimension(100,25));
		menuBar.add(menu3);                 // add the menu to the menu bar
		JMenuItem fileItem1 = new JMenuItem("Show All"); // create a menu item
		fileItem1.setPreferredSize(new Dimension(97,25));
		fileItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton1.setSelected(true); // set the radio button
				myModel.setColumnCount(0);	// clear the tables rows and columns
				myModel.setRowCount(0);
				
					try {
						printAccounts(theFactory.getAllAccounts());
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				
				listLabel.setText("Show All");	// set the list label to "Linked List"
				listLabel.setForeground(Color.RED);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.red));
				outputPanel.setBackground(Color.RED);
				startButton.setText("Show All");
				buttonCheck = 1;	// set button check
				showHide(showallPanel);
			}
		});
		menu3.add(fileItem1);             //add the menu item
		menu3.add(new JSeparator());          // add a separator to the menu
		
		JMenuItem fileItem4 = new JMenuItem("Show History"); // create a menu item
		fileItem4.setPreferredSize(new Dimension(97,25));
		fileItem4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton4.setSelected(true);
				listLabel.setText("Show History");
				startButton.setText("History");
				listLabel.setForeground(Color.GREEN);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.GREEN));
				outputPanel.setBackground(Color.GREEN);
				showHide(historyPanel);
				buttonCheck = 4;
				clearPanel();
			}
		});
		menu3.add(fileItem4);             //add the menu item
		
		menu3.add(new JSeparator());          // add a separator to the menu
		JMenuItem fileItem5 = new JMenuItem("search"); // create a menu item
		fileItem5.setPreferredSize(new Dimension(97,25));
		fileItem5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton5.setSelected(true);
				listLabel.setText("Search");
				startButton.setText("Search");
				listLabel.setForeground(Color.YELLOW);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.YELLOW));
				outputPanel.setBackground(Color.YELLOW);
				buttonCheck = 5;
				showHide(searchPanel);
				clearPanel();
				searchOptions.setVisible(true);
				searchLabel.setText("Please Choose Search Options :");
			}
		});
		menu3.add(fileItem5);             //add the menu item
		/*
		 *  "Lodge" drop down options
		 */
		JMenu menu4 = new JMenu("Lodge");    // create the forth menu option
		menu4.setMnemonic(KeyEvent.VK_L);
		menu4.setPreferredSize(new Dimension(100,25));
		menuBar.add(menu4);                 // add the menu to the menu bar
		JMenuItem fileItem2 = new JMenuItem("Deposit"); // create a menu item
		fileItem2.setPreferredSize(new Dimension(97,25));
		fileItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton2.setSelected(true);
				listLabel.setText("Deposit");
				startButton.setText("Deposit");
				listLabel.setForeground(Color.BLUE);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLUE));
				outputPanel.setBackground(Color.BLUE);
				showHide(depWitPanel);
				buttonCheck = 2;
				clearPanel();
				
			}
		});
		menu4.add(fileItem2);             //add the menu item
		menu4.add(new JSeparator());          // add a separator to the menu
		
		JMenuItem fileItem3 = new JMenuItem("Withdraw"); // create a menu item
		fileItem3.setPreferredSize(new Dimension(97,25));
		fileItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton3.setSelected(true);
				listLabel.setText("Withdraw");
				startButton.setText("Withdraw");
				listLabel.setForeground(Color.BLACK);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
				outputPanel.setBackground(Color.BLACK);
				showHide(depWitPanel);
				buttonCheck = 3;
				clearPanel();
			}
		});
		menu4.add(fileItem3);             //add the menu item
		/*
		 *  "Analytics" drop down options
		 */
		JMenu menu5 = new JMenu("Analytics");    // create the Fifth menu option
		menu5.setMnemonic(KeyEvent.VK_A);
		menu5.setPreferredSize(new Dimension(100,25));
		menuBar.add(menu5);                 // add the menu to the menu bar
		JMenuItem fileItem9 = new JMenuItem("Statistics "); // create a menu item
		fileItem9.setPreferredSize(new Dimension(97,25));
		fileItem9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton9.setSelected(true);
				listLabel.setText("Statistics");
				startButton.setText("Statistics");
				listLabel.setForeground(Color.PINK);
				centerPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.PINK));
				outputPanel.setBackground(Color.PINK);
				buttonCheck = 9;	// set button check
				statsOptions.setSelectedIndex(0);
				showHide(statsPanel);				
				clearPanel();

			}
		});
		menu5.add(fileItem9);             //add the menu item        
		setJMenuBar(menuBar);        // add the menu bar to the frame
	}
	
	
	/**
	 * This is the method that looks after the main action listeners for the buttons on screen
	 * Very extensive error checking was employed
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startButton||e.getSource()== searchText||e.getSource() == amountText) 
		{
			if(radioButton1.isSelected())// Show
			{		// Show All the Accounts - 'L' in CRUDL
					try {
						printAccounts(theFactory.getAllAccounts());
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}	
			}
			else if(radioButton2.isSelected())// Deposit
			{	// Deposit Money in the Accounts - 'U' in CRUDL
				if(checkIntFields(accountText)) // Check that the account number is valid i.e. not text
				{
					if(checkDoubleFields(amountText)) // Check that the deposit number is valid i.e. not text
					{
						Double amount = Double.parseDouble(amountText.getText()); // Parse the amount
						
						if(amount > 0) // if amount is greater that zero
						{
								try { // Deposit the money
									theFactory.depositMoney(Integer.parseInt(accountText.getText()), amount);
								} catch (NumberFormatException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Deposit Money To The Accounts\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								} catch (RemoteException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Deposit Money To The Accounts\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}
							
							
								try { // Print out the changed account information
									printAccounts(theFactory.getAccountById(Integer.parseInt(accountText.getText())));
								} catch (NumberFormatException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								} catch (RemoteException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}
							
							amountText.setText("");
							accountText.setText("");
						}else
						{	// Amount is less than zero. 
							// If less than zero the user could change the account balance. 
							JOptionPane.showMessageDialog(null,"Invalid Amount Entered","Deposit Error",JOptionPane.ERROR_MESSAGE);
							
								try {
									printAccounts(theFactory.getAllAccounts());
								} catch (RemoteException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}
							amountText.setText("");
						}
						
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Invalid Amount Entered","Deposit Error",JOptionPane.ERROR_MESSAGE);
						
							try {
								printAccounts(theFactory.getAllAccounts());
							} catch (RemoteException e1) {
								JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
										"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
						
						amountText.setText("");
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Account Number Entered","Deposit Error",JOptionPane.ERROR_MESSAGE);
					
						try {
							printAccounts(theFactory.getAllAccounts());
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
					accountText.setText("");
				}
			}
			else if(radioButton3.isSelected())// Withdraw
			{	// Withdraw Money from the Accounts - 'U' in CRUDL
				if(checkIntFields(accountText))
				{
					if(checkDoubleFields(amountText))
					{
						Double amount = Double.parseDouble(amountText.getText());
						
						if(amount > 0)
						{
						
							String action = "";
							
								try { // Withdraw the money
									action = theFactory.withdrawMoney(Integer.parseInt(accountText.getText()), amount);
								} catch (NumberFormatException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Withdraw Money To The Accounts\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								} catch (RemoteException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Deposit Money To The Accounts\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}
							
							// check the success message and give the relevant error or messages
							if(action.equalsIgnoreCase("success")){ 
								
									try { // Print out the changed account information
										printAccounts(theFactory.getAccountById(Integer.parseInt(accountText.getText())));
									} catch (NumberFormatException e1) {
										JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
												"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									} catch (RemoteException e1) {
										JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
												"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									}
								
								accountText.setText("");
								amountText.setText("");
							}
							else if(action.equalsIgnoreCase("insufficient")){
								
								JOptionPane.showMessageDialog(null,"Insufficient funds\n"+ 
										"please try again.","Withdraw Error",JOptionPane.ERROR_MESSAGE);
								amountText.setText("");
								
							}else{
								
								JOptionPane.showMessageDialog(null,"Account Number Does Not Exist\n"+ 
										"please try again.","Withdraw Error",JOptionPane.ERROR_MESSAGE);
								accountText.setText("");
								amountText.setText("");
							}
						}else
						{
							JOptionPane.showMessageDialog(null,"Invalid Amount Entered","Deposit Error",JOptionPane.ERROR_MESSAGE);
							
								try {
									printAccounts(theFactory.getAllAccounts());
								} catch (RemoteException e1) {
									JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
											"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}
							
							amountText.setText("");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Invalid Amount Entered","Withdraw Error",JOptionPane.ERROR_MESSAGE);
						
							try {
								printAccounts(theFactory.getAllAccounts());
							} catch (RemoteException e1) {
								JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
										"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
						
						amountText.setText("");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Account Number Entered","Withdraw Error",JOptionPane.ERROR_MESSAGE);
					
						try {
							printAccounts(theFactory.getAllAccounts());
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
					accountText.setText("");
				}
			}
			else if(radioButton4.isSelected())// Show History
			{	// Show History of the Accounts i.e. Show Transactions - 'R' in CRUDL
				if(checkIntFields(accText_h))
				{
					// Show the Transactions
					showTransactions(Integer.parseInt(accText_h.getText()));
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Account Number Entered","Withdraw Error",JOptionPane.ERROR_MESSAGE);
					
						try {
							printAccounts(theFactory.getAllAccounts());
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
					accText_h.setText("");
				}
			}
			else if(radioButton5.isSelected()) // Search
			{	// Search the Accounts - 'R' in CRUDL
				if(searchText.getText().trim().length() == 0) // Check for spaces
				{
					JOptionPane.showMessageDialog(null,"Please Enter a Value To Search","Search Warning",JOptionPane.WARNING_MESSAGE);
						searchText.setText("");
						searchOptions.setSelectedIndex(0);
				}
				else if(checkIntFields(searchText)&&searchOptions.getSelectedItem().equals("Account Number"))
				{
						try { // check by Id in the accounts
							printAccounts(theFactory.getAccountById(Integer.parseInt(searchText.getText())));
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
				}
				else
				{
						try {	// check by field in the accounts
							printAccounts(theFactory.getAccountByField(searchText.getText(),(String)searchOptions.getSelectedItem()));
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
				}
			}
			else if(radioButton6.isSelected()||radioButton7.isSelected()) // New Account - Update
			{	// Set Up New Account or Update the Accounts - 'U & C' in CRUDL
				if(checkIntFields(accText_n)) // Check the account number
				{
					if(checkDoubleFields(balText_n)) // Check the amount
					{
						if(checkDoubleFields(ageText_n)) // Check the age
						{	// Check for spaces
							if(fText_n.getText().trim().length() != 0 && lText_n.getText().trim().length() != 0 && streetText_n.getText().trim().length() != 0 && 
									townText_n.getText().trim().length() != 0 && regionText_n.getText().trim().length() != 0 && countyryText_n.getText().trim().length() != 0)
							{
								if(buttonCheck == 6) // New Account
								{
										try { // if the account number is not there - true
											if(theFactory.accountNumberCheck(Integer.parseInt(accText_n.getText())))
											{
												// New Account - take in all the account information
												int accountNum = Integer.parseInt(accText_n.getText());
												String fName = fText_n.getText();
												String lName = lText_n.getText();
												int age = Integer.parseInt(ageText_n.getText());
												String sName = streetText_n.getText();
												String town = townText_n.getText();
												String region = regionText_n.getText();
												String country = countyryText_n.getText();
												double balance = Double.parseDouble(balText_n.getText());
												// Create the new account
												theFactory.createAccount(accountNum, fName, lName, age, sName, town, region, country, balance);
												// show the new account
												printAccounts(theFactory.getAccountById(accountNum));
												clearPanel(); // Clear the panel
											}
											else
											{
												JOptionPane.showMessageDialog(null,"Account Number Already In Use.\n Please Try Another Account Number","New Account Error",JOptionPane.ERROR_MESSAGE);
												clearPanel();
											}
										} catch (NumberFormatException e1) {
											JOptionPane.showMessageDialog(null,"Couldn't Create The New Account\n"+ 
													"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
											e1.printStackTrace();
										} catch (HeadlessException e1) {
											JOptionPane.showMessageDialog(null,"Couldn't Create The New Account\n"+ 
													"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
											e1.printStackTrace();
										} catch (RemoteException e1) {
											JOptionPane.showMessageDialog(null,"Couldn't Create The New Account\n"+ 
													"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
											e1.printStackTrace();
										}
								}else{ // Update	
										try { // if the account number is not there
											if(theFactory.accountNumberCheck(Integer.parseInt(accText_n.getText())))
											{
												JOptionPane.showMessageDialog(null,"Account Number Not Found.\n Please Try Again.","New Account Error",JOptionPane.ERROR_MESSAGE);
												clearPanel();
											}else{ // valid account number
												// Update - Take in the account information
												int accountNum = Integer.parseInt(accText_n.getText());
												String fName = fText_n.getText();
												String lName = lText_n.getText();
												int age = Integer.parseInt(ageText_n.getText());
												String sName = streetText_n.getText();
												String town = townText_n.getText();
												String region = regionText_n.getText();
												String country = countyryText_n.getText();
												double balance = Double.parseDouble(balText_n.getText());
												// Up dat the account information
												theFactory.updateAccount(accountNum, fName, lName, age, sName, town, region, country, balance);
												// show the updated account information
												printAccounts(theFactory.getAccountById(accountNum));
												clearPanel(); // Clear the panel
											}
										} catch (NumberFormatException e1) {
											JOptionPane.showMessageDialog(null,"Couldn't Update The New Account\n"+ 
													"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
											e1.printStackTrace();
										} catch (HeadlessException e1) {
											JOptionPane.showMessageDialog(null,"Couldn't Update The New Account\n"+ 
													"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
											e1.printStackTrace();
										} catch (RemoteException e1) {
											JOptionPane.showMessageDialog(null,"Couldn't Update The New Account\n"+ 
													"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
											e1.printStackTrace();
										}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null,"All Fields Must Be Filled in.\nPlease Fill In Empty Fields","Update Error",JOptionPane.ERROR_MESSAGE);
								fText_n.setText("");
								lText_n.setText("");
								streetText_n.setText("");
								townText_n.setText("");
								regionText_n.setText("");
								countyryText_n.setText("");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Invalid Age Entered.\nPlease Only Enter Integer Amounts","Update Error",JOptionPane.ERROR_MESSAGE);
							ageText_n.setText("");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Invalid Inital Balance Entered","Update Error",JOptionPane.ERROR_MESSAGE);
						balText_n.setText("");
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Account Number Entered","Update Error",JOptionPane.ERROR_MESSAGE);
					accText_n.setText("");
				}
				
				
			}
			else if(radioButton8.isSelected()) // Delete
			{	// Show All the Accounts - 'L' in CRUDL
				if(checkIntFields(searchText))
				{	// Create a waring that the user is about to delete an account
					int n = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to Delete this Account?",
						"Delete The Account",
						JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
					if(n == 0) // if ok
					{
							try { // Delete the account
								if(theFactory.deleteAccount(Integer.parseInt(searchText.getText())))
								{
									printAccounts(theFactory.getAllAccounts());
									JOptionPane.showMessageDialog(null,"The Account Has Been Deleted");	
									searchText.setText("");
								}else{
									JOptionPane.showMessageDialog(null,"The Account Number Was Not Found and \nCould not Be Deleted\nPlease Try Again.",
										"Deletion Error",JOptionPane.ERROR_MESSAGE);
									searchText.setText("");
								}
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(null,"Couldn't Delete The New Account\n"+ 
										"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							} catch (HeadlessException e1) {
								JOptionPane.showMessageDialog(null,"Couldn't Delete The New Account\n"+ 
										"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							} catch (RemoteException e1) {
								JOptionPane.showMessageDialog(null,"Couldn't Delete The New Account\n"+ 
										"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
						
						
					}else
					{
						searchText.setText("");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Invalid Account Number Entered","Deletion Error",JOptionPane.ERROR_MESSAGE);
					
						try {
							printAccounts(theFactory.getAllAccounts());
						} catch (RemoteException e1) {
							JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
									"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					
					searchText.setText("");
				}
			}
			else // Statistics
			{	// Statistics of the Accounts - 'R' in CRUDL
				// if the comdo box option is 'Region Vrs Balance'
				// Region Vrs Balance - 3D Bar Chart
				if(statsOptions.getSelectedItem().equals("Region Vrs Balance")){
					boolean haveResults = true;
					Map<String, Double> data = null;
					try { // Get the info on regions Vrs balance
						data = theFactory.getStasAVStatB("Region","Balance");
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Could Not Generate The Desired Statistics\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						haveResults = false;
					}
					if(haveResults){ // if there is results
						// Create a simple Bar chart
						// Create a default Category dataset and put in the result values and keys
						DefaultCategoryDataset dataset = new DefaultCategoryDataset();
						
						for(String key : data.keySet())
						{
							dataset.setValue(data.get(key),key, key);
						}
						// Create the 3D bar chart
						JFreeChart chart = ChartFactory.createBarChart3D("Region Vrs Balance",
						"Region", "Total Balances", dataset, PlotOrientation.HORIZONTAL,
						true, true, false);
						// Set the background and color of the chart
						chart.setBackgroundPaint(Color.PINK); // Set the background colour of the chart
						chart.getTitle().setPaint(Color.BLUE); // Adjust the colour of the title
						
						// Set the plot line to red and the background to white
						CategoryPlot p = chart.getCategoryPlot();
						p.setBackgroundPaint(Color.WHITE); // Modify the plot background
						p.setRangeGridlinePaint(Color.RED);
						// Create the frame
						ChartFrame frame = new ChartFrame("Region Vrs Balance - Bar Chart", chart);
						frame.setIconImage(icon.getImage()); // Add the icon to the frame
						frame.setVisible(true); // Set visiable
						frame.setSize(new Dimension(800,400)); // Set the size of the frame.
					}
					
				// Town Vrs Balance - 3D Bar Chart	 
				}else if(statsOptions.getSelectedItem().equals("Town Vrs Balance")){
					boolean haveResults = true;
					Map<String, Double> data = null;
					try {
						data = theFactory.getStasAVStatB("Town","Balance");
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Could Not Generate The Desired Statistics\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						haveResults = false;
					}
					
					if(haveResults){ // if there is results
						// Create a simple Bar chart
						DefaultCategoryDataset dataset = new DefaultCategoryDataset();
						
						for(String key : data.keySet())
						{
							dataset.setValue(data.get(key), key , key);
						}
						
						JFreeChart chart = ChartFactory.createBarChart3D("Town Vrs Balance",
						"Town/City", "Total Balances", dataset, PlotOrientation.HORIZONTAL,
						true, true, false);
						
						chart.setBackgroundPaint(Color.PINK); // Set the background colour of the chart
						chart.getTitle().setPaint(Color.BLUE); // Adjust the colour of the title
						
						CategoryPlot p = chart.getCategoryPlot();
						p.setBackgroundPaint(Color.WHITE); // Modify the plot background
						p.setRangeGridlinePaint(Color.RED);
						
						ChartFrame frame = new ChartFrame("Town Vrs Balance - Bar Chart", chart);
						frame.setIconImage(icon.getImage());
						frame.setVisible(true);
						frame.setSize(new Dimension(900,400));
					}
				// Age of Clients - Line Graph	
				}else if(statsOptions.getSelectedItem().equals("Age of Clients")){
					// Age of Clients
					boolean haveResults = true;
					Map<Integer, Integer> dataAge = null;
					try {
						dataAge = theFactory.getAgeOfClients();
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Could Not Generate The Desired Statistics\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						haveResults = false;
					}
					
					if(haveResults){ // if there is results
						// Create a simple XY chart
						XYSeries series = new XYSeries("Age of Clients");
						
						for(int key : dataAge.keySet())
						{
							series.add(key, dataAge.get(key));
						}
						
						// Add the series to your data set
						XYSeriesCollection dataset = new XYSeriesCollection();
						dataset.addSeries(series);
						
						// Generate the line graph
						JFreeChart chart = ChartFactory.createXYLineChart(
						"Age of Clients", // Title
						"Age Of Clients", // x-axis Label
						"Number of Clients", // y-axis Label
						dataset, // Dataset
						PlotOrientation.VERTICAL, // Plot Orientation
						true, // Show Legend
						true, // Use tooltips
						false // Configure chart to generate URLs?
						);
						
						chart.setBackgroundPaint(Color.PINK); // Set the background colour of the chart
						chart.getTitle().setPaint(Color.BLUE); // Adjust the colour of the title
						
						ChartFrame frame = new ChartFrame("Age of Clients - Line Graph", chart);
						frame.setIconImage(icon.getImage()); // Add the icon to the frame
						frame.setVisible(true);
						frame.setSize(new Dimension(800,400));
					}
				}else{ // Overall Account Statistics - JFrame
					boolean haveResults = true;
					
					ArrayList<Double> accStats = null;
					try {
						accStats = theFactory.getAccountStatistics();
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null,"Could Not Generate The Desired Statistics\n"+ 
								"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						haveResults = false;
					}
					if(haveResults){ // if there is results
						// Create a JFrame for the Overall Account Statistics
						final JFrame statsFrame = new JFrame("Overall Account Statistics");
						statsFrame.setIconImage(icon.getImage()); // add the icon
						statsFrame.setSize(500,500);  // Set the size
						// Set Dispose on close so it only closes the current JFrame and not the whole program
						statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						statsFrame.setVisible(true);
						
						// Overall Account Statistics screen
						JPanel panelData = new JPanel(new GridLayout(12,2)); // create a panel
						panelData.setVisible(true);
						panelData.setBorder(new EmptyBorder(10,10,10,10));
						panelData.setPreferredSize(new Dimension(400,350));
						panelData.setBackground(Color.PINK);
						
						JLabel tAccounts, tMoney,aMoney,bAccount,sAccount,aAge;
						JTextField tAccountsText,tMoneyText, aMoneyText, bAccountText, sAccountText, aAgeText;
						
						tAccounts = new JLabel("Total Amount of Accounts: ");
						tAccounts.setFont(new Font("Verdana", Font.BOLD, 12));
						tAccounts.setPreferredSize(new Dimension(80,30));
						
						tAccountsText = new JTextField();
						tAccountsText.setMargin(new Insets(0, 5, 0, 0));
						tAccountsText.setPreferredSize(new Dimension(50,30));
						tAccountsText.setEditable(false);
						
						tMoney = new JLabel("Total Money in Accounts: ");
						tMoney.setFont(new Font("Verdana", Font.BOLD, 12));
						tMoney.setPreferredSize(new Dimension(80,30));
						
						tMoneyText = new JTextField();
						tMoneyText.setMargin(new Insets(0, 5, 0, 0));
						tMoneyText.setPreferredSize(new Dimension(50,30));
						tMoneyText.setEditable(false);
						
						aMoney = new JLabel("Average Money in Accounts: ");
						aMoney.setFont(new Font("Verdana", Font.BOLD, 12));
						aMoney.setPreferredSize(new Dimension(80,30));
						
						aMoneyText = new JTextField();
						aMoneyText.setMargin(new Insets(0, 5, 0, 0));
						aMoneyText.setPreferredSize(new Dimension(50,30));
						aMoneyText.setEditable(false);
						
						bAccount = new JLabel("Biggest Balance: ");
						bAccount.setFont(new Font("Verdana", Font.BOLD, 12));
						bAccount.setPreferredSize(new Dimension(80,30));
						
						bAccountText = new JTextField();
						bAccountText.setMargin(new Insets(0, 5, 0, 0));
						bAccountText.setPreferredSize(new Dimension(50,30));
						bAccountText.setEditable(false);
						
						sAccount = new JLabel("Smallest Balance: ");
						sAccount.setFont(new Font("Verdana", Font.BOLD, 12));
						sAccount.setPreferredSize(new Dimension(80,30));
						
						sAccountText = new JTextField();
						sAccountText.setMargin(new Insets(0, 5, 0, 0));
						sAccountText.setPreferredSize(new Dimension(50,30));
						sAccountText.setEditable(false);
						
						aAge = new JLabel("Average Age of Clients: ");
						aAge.setFont(new Font("Verdana", Font.BOLD, 12));
						aAge.setPreferredSize(new Dimension(80,30));
						
						aAgeText = new JTextField();
						aAgeText.setMargin(new Insets(0, 5, 0, 0));
						aAgeText.setPreferredSize(new Dimension(50,30));
						aAgeText.setEditable(false);
						
						// close Button - to close only the frame
						bExit = new JButton("Close");
						bExit.setPreferredSize(new Dimension(50,30));
						add(bExit);
						
						bExit.addActionListener(new ActionListener() 
						{			
							@Override
							public void actionPerformed(ActionEvent e) 
							{
								statsFrame.dispose(); // close only the frame
								
							}
						});
						
						
						panelData.add(tAccounts);panelData.add(tAccountsText);
						// Add padding between components
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(tMoney);panelData.add(tMoneyText);
						// Add padding between components
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(aMoney);panelData.add(aMoneyText);
						// Add padding between components
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(bAccount);panelData.add(bAccountText);
						// Add padding between components
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(sAccount);panelData.add(sAccountText);
						// Add padding between components
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(aAge);panelData.add(aAgeText);
						// Add padding between components
						panelData.add(new Box.Filler(new Dimension(5,5),new Dimension(5,5),new Dimension(10,10)));
						panelData.add(bExit);
						
						tMoneyText.setText(df.format(accStats.get(0)));
						aMoneyText.setText(df.format(accStats.get(1)));
						bAccountText.setText(df.format(accStats.get(2)));
						sAccountText.setText(df.format(accStats.get(3)));
						// Because the returning figures were double. I converted them in to
						// integers e.g. average age would be 34 instead of 34.876539
						aAgeText.setText(""+Integer.valueOf(accStats.get(4).intValue()));
						tAccountsText.setText(""+Integer.valueOf(accStats.get(5).intValue()));
						
						statsFrame.getContentPane().add(panelData); // Add the panel to the frame
					}
				}
			}
		}
		// Listener for the Reset Button 
		if (e.getSource() == reSetButton) 
		{
			clearPanel();
		} 
		if(e.getSource() == redoButton) // Listener for the Redo Button on search option
		{
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			
			searchOptions.setSelectedIndex(0);
			searchText.setText("");
		}
		if(e.getSource() == redoStatButton) // Listener for the Redo Button on the Statistics option  
		{
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			
			statsOptions.setSelectedIndex(0);
		}
	}
	
	private void clearPanel(){
		// Used the buttonCheck used through out the program as a flag.
		if(buttonCheck == 1) // Show All option
		{
			myModel.setColumnCount(0); // clear the table columns
			myModel.setRowCount(0);		// clear the rows
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			
		}
		if(buttonCheck == 2 || buttonCheck == 3 ){ // withdraw and deposit clear
			accountText.setText("");
			amountText.setText("");
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			
		}
		if(buttonCheck == 5||buttonCheck == 8) // search / delete clear
		{
			searchText.setText("");
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			
		}
		if(buttonCheck == 4){ // show history clear
			accText_h.setText("");
			fText_h.setText("");
			lText_h.setText("");
			addText_h.setText("");
			countyryText_h.setText("");
			balText_h.setText("");
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			
		}if(buttonCheck == 6||buttonCheck == 7) // new account/ update clear
		{
			accText_n.setText("");
			fText_n.setText("");
			lText_n.setText("");
			ageText_n.setText("");
			streetText_n.setText("");
			townText_n.setText("");
			regionText_n.setText("");
			countyryText_n.setText("");
			balText_n.setText("");
			
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			
		}if(buttonCheck == 9) // Statistics
		{
				try {
					printAccounts(theFactory.getAllAccounts());
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
							"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			
			statsOptions.setSelectedIndex(0);
		}
	}
	/**
	 * This method show the panel you want and hides the other
	 * @param panel - The panel you want to set to visible
	 */
	private void showHide(JPanel panel){
		// Go through the list of panels
		for(JPanel temp : myPanels)
		{
			temp.setVisible(false); // Set them all the be invisible
		}
		panel.setVisible(true); // Set the chosen panel to visible
		
	}
	/**
	 * checkIntFields - Makes sure a valid integer value has been entered into the relevant field 
	 * @param name - name of the text field you are checking
	 * @return boolean - true or false
	 */
	private boolean checkIntFields(JTextField name)
	{
		int check = 0;
		try
		{
			check = Integer.parseInt(name.getText());  // try and parse the number to an integer
			return true; 	// Is valid
		}
		catch(NumberFormatException e)
		{
			return false; // Is not valid
		}
	}
	/**
	 * checkDoubleFields - Makes sure a valid Double value has been entered into the relevant field 
	 * @param name - the name of the jTextField that you are checking
	 * @return boolean - true or false
	 */
	private boolean checkDoubleFields(JTextField name)
	{
		double check = 0;
		try
		{
			check = Double.parseDouble(name.getText());  // try and parse the number to an double
			return true;	// Is valid
		}
		catch(NumberFormatException e)
		{
			return false; // Is not valid
		}
	}
	/**
	 * This method looks after the printing of the list of account to the screen
	 * This is used by all panels except show history
	 * @param tempList - List of items to be shown on the JTable
	 */
	private void printAccounts(ArrayList<String> tempList) 
	{
		if(tempList.size()== 0) // If the list is empty
		{
			
			try { // Show all current accounts
				printAccounts(theFactory.getAllAccounts());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null,"Couldn't Show All Accounts\n"+ 
						"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null,"Your Input Criteria Could Not Be Found\n" +
				"Please Try Again Later.","Account Warning",JOptionPane.ERROR_MESSAGE);
			searchText.setText("");
			searchOptions.setSelectedIndex(0);
		}else{
		
			myModel.setColumnCount(0);	// clear the table
			myModel.setRowCount(0);
			tableMain.setBackground(Color.WHITE);	// change the colour of the table
			String[] firstRow = listSetHeader;	// add the heading for the table
			
			int listLength = tempList.size(); // find the size of the array list 
			table = new String[listLength / 9][9];	// as there is 9 items per line set the 2d array
			int memberCount = 0;	// count the different objects so they go into the right place 
			for (int i = 0; i < listLength / 9; i++) // loop through the rows
			{
				for (int j = 0; j < 9; j++) // loop through the columns
				{
					table[i][j] = tempList.get(memberCount); // add the object to the desired cell
					memberCount++;	// increment the count
				}
			}
			for(int i=0;i<firstRow.length;i++)	// add the headings to the table 
			{
				myModel.addColumn(firstRow[i]);
			}
	
			for(int i=0;i<table.length;i++)  // search through the 2D Array so you can add the rows to the table
			{
				String[] temp = new String[table[0].length];
				for(int j=0;j<table[0].length;j++)
				{
					temp[j] = table[i][j];
				}
				myModel.addRow(temp); // add the row to the table
			}
			// Set the size for the 0,4,5,6th rows differently to the others 
			for (int i = 0; i < tableMain.getColumnCount(); i++) {
				TableColumn column = tableMain.getColumnModel().getColumn(i);
				
			    if (i==0||i == 4 || i == 5||i==6) {
			        column.setPreferredWidth(100); 
			    } else {
			        column.setPreferredWidth(50);
			    }
			   
			}
		}
	}
	/**
	 * This method lets you show the account transaction history for the account
	 * @param accountNumber - The account number that you want to show the account history of
	 */
	private void showTransactions(int accountNumber) {
		
		// Return the values to show in the top part of the screen
		ArrayList<String> tempAccount = null;
		
		try {
			tempAccount = theFactory.getAccountById(accountNumber);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,"Couldn't Find the Account Information\n"+ 
					"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		fText_h.setText(tempAccount.get(1));
		lText_h.setText(tempAccount.get(2));
		addText_h.setText(tempAccount.get(4)+", "+tempAccount.get(5));
		countyryText_h.setText(tempAccount.get(6)+", "+tempAccount.get(7));
		String bal = tempAccount.get(8);
		String unformattedBal = bal.substring(2,bal.length()); 		
		balText_h.setText(unformattedBal);
		
		// Get the transaction for the desired account and show them on the table
		// This time using a Vector
		Vector<Transactions> tempList = null;
		try {
			tempList = theFactory.showAllTranactions(accountNumber);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,"Could Not Show All Tranactions\n"+ 
					"Please Contact the I.T. Department","System Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		myModel.setColumnCount(0);	// clear the table
		myModel.setRowCount(0);
		tableMain.setBackground(Color.WHITE);	// change the colour of the table
		String[] firstRow = transHeader;	// add the heading for the JTable
		
		int listLength = tempList.size(); // find the size of the array list 
		
		table = new String[listLength][3];	// as there is 6 items per line set the 2d array
		int rowCount = 0;
		
		for(Transactions acc : tempList){
			// Add the transactions to the 2d array list
			table[rowCount][0] = acc.getTimeStamp();
			table[rowCount][1] = acc.getTrans();
			table[rowCount][2] = acc.getCBalance();
			rowCount++;
		}
		for(int i=0;i<firstRow.length;i++)	// add the headings to the table 
		{
			myModel.addColumn(firstRow[i]);
		}

		for(int i=0;i<table.length;i++)  // search through the 2D Array so you can add the rows to the table
		{
			String[] temp = new String[table[0].length];
			for(int j=0;j<table[0].length;j++)
			{
				temp[j] = table[i][j];
			}
			myModel.addRow(temp); // add the row to the table
		}
	}
	
	/**
	 * This is the Main Method 
	 * @param args
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		new MainGuiClass("Assignment 3 - The Bank Of Rory");
	}

}
