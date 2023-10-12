package PrivateLab;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Bank 
{
	public static int accNum = 0; // The account number and PIN for different accounts
	public static int PIN = 1000;
	protected ArrayList<Account> accounts; // The arraylist for all the accounts in one bank
	protected ArrayList<Transaction> transactions; // The arraylist for all the uncleared transactions
	
	public Bank()
	{
		this.accounts = new ArrayList<>();
		this.transactions = new ArrayList<>();
	}
	
	public void openAccount(String name, String address, String dob, String type) throws IOException
	{
		String filePath = new File("").getAbsolutePath(); // read all credit information from files
		BufferedReader reader = new BufferedReader(new FileReader(filePath + "/src/PrivateLab/creditfile.txt"));
		String line = reader.readLine();
		ArrayList<Customer> customerList = new ArrayList<>();
		while(line != null)
		{
			String[] words = line.split(" ");
			boolean credit = Boolean.parseBoolean(words[3]);
			Customer newcustomer = new Customer(words[0],words[1],words[2],credit);
			customerList.add(newcustomer);
			line = reader.readLine();
		}
		reader.close();
		boolean find = false;
		for(Customer cus: customerList) // iterate all customers to check the personal information
		{								// and credit
			if(cus.getName().equals(name) && cus.getAddress().equals(address) && cus.getDOB().equals(dob))
			{
				find = true;
				if(cus.getCredit() == false)
				{
					System.out.println("Sorry we can't open account for you because of your credit issues.");
				}
				else
				{
					if(type.equals("Current")) // The withdraw limit is set to 100 for current account
					{
						CurrentAccount newaccount = new CurrentAccount(accNum, PIN, 0, false, 100);
						System.out.println("We have opened an "+ type + " account for "+ name + " with "+ accNum + 
								" as accNum and "+ PIN+ " as PIN.");
						accounts.add(newaccount);
						accNum++; PIN++;
					}
					else if (type.equals("Junior"))
					{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date nowtime = new Date();
						String nowTimeStr = sdf.format(nowtime);
						String nowTime[] = nowTimeStr.split("-");
						String birthTime[] = dob.split("-");
						if(Integer.parseInt(nowTime[0]) - Integer.parseInt(birthTime[0]) < 16) 
						{ //Junior account only allows teenagers under 16 to open.
							JuniorAccount newaccount = new JuniorAccount(accNum, PIN, 0, false);
							System.out.println("We have opened an "+ type + " account for "+ name + " with "+ accNum + 
									" as accNum and "+ PIN+ " as PIN.");
							accounts.add(newaccount);
							accNum++; PIN++;
						}
						else
						{
							System.out.println("Your age is beyond 16 so we can't open a junior account for you.");
						}
					}
					else if(type.equals("Saver"))
					{
						SaverAccount newaccount = new SaverAccount(accNum, PIN, 0, false);
						System.out.println("We have opened an "+ type + " account for "+ name +" with "+ accNum + 
								" as accNum and "+ PIN+ " as PIN.");
						accounts.add(newaccount);
						accNum++; PIN++;
					}
					else
					{
						System.out.println("You entered wrong type name. Please try again.");
					}
				}
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the personal information you provided. Please check out.");
		}
	}
	
	public void depositFund(int accNum, int PIN, double amount) // deposit cash for different accounts in
	{															// the bank
		boolean find = false;
		for(Account target_account: accounts)
		{
			if(accNum == target_account.accNum && PIN == target_account.PIN)
			{
				target_account.depositCash(amount);
				find = true;
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public void transferFund(int from_accNum, int from_PIN, int to_accNum, double amount)
	{// transfer fund between different accounts
		boolean find = false;
		for(Account target_account: accounts)
		{
			if(from_accNum == target_account.accNum && from_PIN == target_account.PIN)
			{
				find = true;
				boolean find_another = false;
				for(Account to_account: accounts)
				{
					if(to_accNum == to_account.accNum)
					{
						find_another = true;
						break;
					}
				}
				if(find_another == false)
				{
					System.out.println("We didn't find the account you want to tranfer moeny to. Please check out.");
				}
				else
				{
					Transaction newtransaction = target_account.transferMoney(to_accNum, amount);
					if(newtransaction != null)
					{
						transactions.add(newtransaction);
					}
				}
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public void clearTransactions() // clear all the uncleared transactions
	{
		for(Transaction one_transaction : transactions)
		{
			int from_accNum = one_transaction.fromAccNum;
			int to_accNum = one_transaction.toAccNum;
			double amount = one_transaction.amount;
			for(Account fromaccount : accounts)
			{
				if(fromaccount.accNum == from_accNum)
				{
					fromaccount.balance = fromaccount.balance - amount;
					System.out.println("Now the balance in the "+ from_accNum+ " is "+ fromaccount.balance);
					break;
				}
			}
			for(Account toaccount : accounts)
			{
				if(toaccount.accNum == to_accNum)
				{
					toaccount.balance = toaccount.balance + amount;
					System.out.println("Now the balance in the "+ to_accNum+ " is "+ toaccount.balance);
					break;
				}
			}
		}
		transactions = new ArrayList<>();
	}
	
	public void withdrawMoney(int accNum, int PIN, double amount) // This function is only used by junior and 
	{															  // current accounts, the saver accounts need 
		boolean find = false;									  // to make an appointment first
		for(Account target_account: accounts)
		{
			if(accNum == target_account.accNum && PIN == target_account.PIN)
			{
				find = true;
				String belong = target_account.getClass().getSimpleName();
				if(belong.equals("CurrentAccount"))
				{
					target_account.withdraw(amount);
				}
				else if(belong.equals("JuniorAccount"))
				{
					target_account.withdraw(amount);
				}
				else
				{
					System.out.println("For a saver account you need to make an appointment first.");
				}
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public void makeAppointment(int accNum, int PIN, int year, int month, int day, double amount)
	{
		boolean find = false;
		for(Account target_account : accounts)
		{
			if(target_account.accNum == accNum && target_account.PIN == PIN)
			{
				find = true;
				String belong = target_account.getClass().getSimpleName();
				if(belong.equals("SaverAccount"))
				{
					target_account.makeAnAppointment(year, month, day, amount);
				}
				else
				{
					System.out.println("Sorry, we can only make appointments for SaverAccount.");
				}
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public void checkAppointment(int accNum, int PIN)
	{
		boolean find = false;
		for(Account target_account: accounts)
		{
			if(target_account.accNum == accNum && target_account.PIN == PIN)
			{
				find = true;
				String belong = target_account.getClass().getSimpleName();
				if(belong.equals("SaverAccount"))
				{
					target_account.checkAppointments();
				}
				else
				{
					System.out.println("Sorry, we can only check the appointments made by SaverAccount.");
				}
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public void suspendAccount(int accNum, int PIN)
	{
		boolean find = false;
		for(Account target_account: accounts)
		{
			if(accNum == target_account.accNum && PIN == target_account.PIN)
			{
				target_account.isSuspended = true;
				find = true;
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public void reinstateAccount(int accNum, int PIN)
	{
		boolean find = false;
		for(Account target_account: accounts)
		{
			if(accNum == target_account.accNum && PIN == target_account.PIN)
			{
				target_account.isSuspended = false;
				find = true;
				break;
			}
		}
		if(find == false)
		{
			System.out.println("We didn't find the account you provided. Please check out.");
		}
	}
	
	public double closeAccount(int accNum, int PIN)
	{
		for(int i = 0; i < accounts.size(); i++)
		{
			if(accounts.get(i).accNum == accNum && accounts.get(i).PIN == PIN)
			{
				double balance = accounts.get(i).balance;
				if(balance >= 0)
				{
					accounts.remove(i);
					System.out.println("The account with "+ accNum + " as accNum and "+ 
									PIN + "as PIN has been closed. The balance of this account is "+ balance+ ".");
					return balance;
				}
				else
				{
					System.out.println("Failed to close the account with "+ accNum + " as accNum and "+
									PIN + " as PIN because of negative balance.");
					return -1;
				}
			}
		}
		System.out.println("We didn't find the account you provided. Please check out.");
		return -1;
	}
	
	
	public static void main(String args[])
	{
		Bank myBank = new Bank();
		try {
			myBank.openAccount("YunweiGuo", "Hebei", "2002-02-04" , "Saver");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myBank.depositFund(0, 1000, 100);
		double returnmoeny = myBank.closeAccount(0, 1000);
		System.out.println(returnmoeny);
		
	}
}
