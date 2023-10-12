package PrivateLab;

public class CurrentAccount extends Account 
{
	private double limit;
	public CurrentAccount(int accNum, int PIN, double balance, boolean isSuspended, double limit)
	{
		super(accNum, PIN, balance, isSuspended);
		this.limit = limit;
	}
	
	public void withdraw(double amount)
	{
		if(isSuspended)
		{
			System.out.println("Sorry, this account is suspended");
		}
		else
		{
			if(this.balance - amount < (-1) * limit)
			{
				System.out.println("For the account "+ accNum+" , we can't "
						+ "withdraw "+amount+" because it will cause overdraft.");
			}
			else
			{
				this.balance = this.balance - amount;
			}
		}
	}
	
}
