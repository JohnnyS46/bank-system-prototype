package PrivateLab;

public class Customer // This class is used to store the credit information of customers in the database.
{
	private String name;
	private String address;
	private String dateOfBirth;
	private boolean personalCredit;
	
	public Customer(String name, String address, String dateOfBirth, boolean credit)
	{
		this.name = name;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.personalCredit = credit;
	}
	public boolean getCredit()
	{
		return this.personalCredit;
	}
	public String getName()
	{
		return this.name;
	}
	public String getAddress()
	{
		return this.address;
	}
	public String getDOB()
	{
		return this.dateOfBirth;
	}
}
