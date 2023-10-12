package PrivateLab;
import java.io.*;
import java.util.ArrayList;

public class CustomerTest 
{
	public static void main(String[] args) throws IOException
	{
		// read customer information from files
		String filePath = new File("").getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader(filePath + "/src/PrivateLab/creditfile.txt"));
		String line = reader.readLine();
		ArrayList<Customer> customerList = new ArrayList<>(); // The information of customers 
															  // will be stored in the ArrayList.
		while(line != null)
		{
			String[] words = line.split(" ");
			boolean credit = Boolean.parseBoolean(words[3]);
			Customer newcustomer = new Customer(words[0],words[1],words[2],credit);
			customerList.add(newcustomer);
			line = reader.readLine();
		}
		reader.close();
		for(Customer cus: customerList) // print the customer information read from file
		{
			System.out.println(cus.getName() + " "+ cus.getAddress() + " "
					+ cus.getDOB() + " "+ cus.getCredit());
		}
	}
}
