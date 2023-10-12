package PrivateLab;
import java.util.*;
import java.text.*;

public class JuniorAccount extends Account
{
	protected HashMap<String, Double> transaction; // this hashMap stores the daily withdraw records
	
	public JuniorAccount(int accNum, int PIN, double balance, boolean isSuspended)
	{
		super(accNum, PIN, balance,isSuspended);
		this.transaction = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowtime = new Date();
		String nowTimeStr = sdf.format(nowtime);
		Double value = 0.0;
		this.transaction.put(nowTimeStr, value);
	}
	
	
	public void withdraw(double amount)
	{
		if(isSuspended)
		{
			System.out.println("Sorry, this account is suspended.");
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowtime = new Date();
			String nowTimeStr = sdf.format(nowtime);
			
			if(this.balance - amount < 0)
			{
				System.out.println("For the account "+ accNum+" , we can't "
						+ "withdraw "+amount+" because it will cause overdraft.");
			}
			else if(amount + (transaction.get(nowTimeStr)).doubleValue() < 100)
			{
				this.balance = this.balance - amount;
				double newvalue = transaction.get(nowTimeStr).doubleValue() + amount;
				Double newamount = newvalue;
				transaction.put(nowTimeStr, newamount);
			}
			else
			{
				System.out.println("For a Junior account you can't withdraw more than 100 yuan per day.");
			}
		}
	}
	
	public static void main(String args[])
	{
		JuniorAccount myAccount = new JuniorAccount(10,1010,20.0,false);
		myAccount.depositCash(3000);
		System.out.println("Now the amount of money in my account is " + myAccount.balance);
		myAccount.withdraw(80);
		System.out.println("Now the amount of money in my account is " + myAccount.balance);
		myAccount.withdraw(80);
	}
	
}
