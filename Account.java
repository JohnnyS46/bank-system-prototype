package PrivateLab;

public abstract class Account 
{
	protected int accNum; // These information is contained in all account types.
	protected int PIN;
	protected double balance;
	protected boolean isSuspended;
	
	public Account(int accNum, int PIN, double balance, boolean isSuspended)
	{
		this.accNum = accNum;
		this.PIN = PIN;
		this.balance = balance;
		this.isSuspended = isSuspended;
	}
	
	public void depositCash(double amount) // Deposit cash corresponds to cleared transaction.
	{									   // The money will directly go to the customers' account.
		if(isSuspended)
		{
			System.out.println("Sorry, this account is suspended.");
		}
		else
		{
			double newbalance = this.balance;
			newbalance = newbalance + amount;
			this.balance = newbalance;
			System.out.println("Now the account "+ accNum + "'s balance is "+ newbalance);
		}
	}
	
	public Transaction transferMoney(int toAccNum, double amount) // TransferMoney corresponds to uncleared transaction.
	{															  // The money will go to account only if the transactions 
																  // are cleared by bank system.
		if(isSuspended)
		{
			System.out.println("Sorry, this account is suspended.");
			return null;
		}
		else
		{
			Transaction newtransaction = new Transaction(this.accNum, toAccNum, amount);
			return newtransaction;
		}
	}
	
	public abstract void withdraw(double amount); // Different type of accounts have different
												  // type of withdraw functions.
	
	
	public void checkAppointments() // These two functions are from SaverAccount class. In order to 
									// use them directly in bank system, we wrote two void functions
									// in the account class.
	{
		
	}
	
	public void makeAnAppointment(int year, int month, int day, double amount)
	{
		
	}
	

}
