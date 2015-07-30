![alt text][logo]
[logo]: https://raw.github.com/rorynee/bankOfRoryRMIAssignment/master/bin/images/borlogo.png  "Bank Of Rory Logo"

# Bank of Rory RMI Assignment

## Assignment Outline
Implementing a 3 Tier Object Oriented System
Objective: This project will create a 3 tier system in Java:

 **Tier 1** – Will consist of a GUI system implemented in Swing. The GUI should consist of a table on which the user can implement CRUD – Create, Read, Update and Delete actions.

 **Tier 2** – Will consist of a Java server. The Java server will communicate with the client using RMI. The Java server will implement the Factory design pattern.

 **Tier 3** – Will consist of Java Objects stored persistently. Do not implement the persistence tier using a DB.

##Assignment concept
I choose to generate the data set around bank customers and account as I felt it lent itself to C.R.U.D.L. operations.

Create – Make a new account

Read – Search, Read transactions, read the statistics

Update – Update account information, deposit money and withdraw money

Delete – Delete an account

List – Show all accounts

I created a bank account system that can be used by bank teller to Create, Read, Update, Delete and List the accounts. As part of the bank accounts I also wanted to keep track of a customer’s transactions (withdraw and deposit) just like a real bank would do.
Also I wanted to be able to generate dynamic statistics in relation to that data so I could generate 3d bar charts, line graph and other data in relation the account held in a bank. 

***
## Installation and Usage

 **1.** Download the files in to Eclipse
 
 **2.** Open 3 command prompts and navigate to the bin directory
 
 In one command shell, in the bin directory **Run** rmic.exe program. This Generates the Stub classes
 
 *In my case with the project bin folder selected > “C:\Program Files\Java\jdk1.7.0_15\bin\rmic.exe” AccountFactory*

 In another of the command shells - start the RMI registry using the command “rmiregistry”
 
 *In my case with the project bin folder selected > “C:\Program Files\Java\jdk1.7.0_15\bin\ rmiregistry.exe”*

 In another command shell – start the server “java ProductServer”
 
 *In my case with the project bin folder selected > java BankOfRoryServer*

 **3.** Open Eclipse and run the file MainGuiClass  
 
***
##System Architecture

I set up the project as described in the “Implementing a 3 Tier Object Oriented System” outline. My ‘AccountFactory’ class implements the ‘AccountInterface’ class. The ‘Account’ class is aggregated to the ‘AccountFactory’ class and the ‘transaction’ class is aggregated to the ‘Account’ class. The Account factory through the Account interface uses RMI’s mechanism of the RMI studs and the RMI registry to connect with the server. The server in turn can be connected by the ‘MainGuiClass’ and using an Identification (URL) to find the target machine where the RMI registry and remote objects are located. Then the ‘MainGuiClass’ requests the RMI registry on the target machine to return an object reference that applies to the well-known name or password. Then using the ‘AccountInterface’ it can pass the objects back and forth to each other.

## Class Descriptions

###BankOfRoryServer

The Bank Of Rory Server binds the well-known (“BankOfRoryServer”) name to the object of the account factory and stores that in the Rmi registry.  

###Account.Java

The Account class outlines all the information involved in a bank account. The Account class also uses a vector of type ‘Transactions’ to hold all the transactions that are made by an account. The types of transactions that are take note of is when the account was created and the deposit and withdraw money. I have also implemented the comparable interface so I will be able to compare one account to another account using the account number. This is used when sorting the account numbers in the table. 

###Transactions

The Transaction class outlines the information that is used to make up a transaction. Here the time stamp is generated using a simple data format and the balance is also noted at the time of the transaction been made. There is no setter methods here are I did not want somebody to be able to change the transactions once they were made. 

###AccountInterface

The account interface acts as an interface for the account factory. The RMI stubs are created using the interface.   
 
###AccountFactory

The account factory holds all the methods for the CRUDL actions.

 Create – createAccount()

 Read – getAccountById(), getAccountByField(), showALLTranactions(), getAgeOfClients, getStasAVStatB(), getAccountStatistics()

 Update – updateAccount(),depositMoney(), withdrawMoney()

 Delete – deleteAccount()

 List – getAllAccounts()

The method called getInstance() makes only one instantiation of the AccountFactory and give back a reference to the object of the account factory if another class requests to make another object of the account factory.
There is also a method to check weather an account number is in the system. I also have a onStart() and an onSave() method to serialize and desterilize the data to file.

###MainGuiClass

The MainGuiClass sets up the Gui that the user sees. As you can see there is a lot of attributes declared in the MinGuiClass. In the code I have reused ‘private JLabel’ a few times as there is a lot of label and did not want to have them untidy.
A few thing to note, I have created an ArrayList of Jpanels to be used by a method to hide all panels and to make visible the panel passed to it (showHide() method).
The table was set up using the defaultTableModel with a 2d array called ‘table’ and two arrays for the heading called ‘listSetHeader’ and ‘transHeader’.
There are two separate decimal format mentioned here to accommodate the information in the table and for the input of new values (new account, update account).  
The variable called ‘buttonCheck’ is used to see what radiobutton is checked so I can clear it.
Also here you see the inclusion of theURL and the AccountInterface used by RMI. The Accouny Factory will be referred to as ‘theFactory’ when it is been called in the GUI. 
  
###Methods

I have employed the use of multiple helper methods here so as not to clutter up the code.

 setUpMenu();	// set up the drop down menu

 setUpPanals();	// set up the panels to be used

 setUpLogo();	// Set up to Logo and the header label

 setUpMiddlePanel();	// set up the middle panel

 setUpRadioPanel();	// set up the panel with the radio buttons

 setUpOutputPanel();	// set up the main display output area
 
The actionPerformed method handles all the events form the user.
There other methods of note hare are the showHide() ( I mentioned earlier ) and the checkIntFields() and CheckDoubleFields(). The latter two function test weather a number is a number and not text.
I also have two different function that print the data to the table because I show the data different in the show history page. (printAccounts() and showTransactions()).



