package PrivateLab;

public class Transaction 
{
	public int fromAccNum;
	public int toAccNum;
	public double amount;
	
	public Transaction(int fromAccNum, int toAccNum, double amount)
	{
		this.fromAccNum = fromAccNum;
		this.toAccNum = toAccNum;
		this.amount = amount;
	}
}
