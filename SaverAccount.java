package PrivateLab;
import java.util.*;
import java.text.*;

public class SaverAccount extends Account
{
	protected HashMap<String, Double> appointment;
	protected int time_interval;
	public SaverAccount(int accNum, int PIN, double balance, boolean isSuspended)
	{
		super(accNum, PIN, balance, isSuspended);
		this.time_interval = 1; // for a saver account you must make an appointment 
								// one day before withdraw.
		appointment = new HashMap<>();
	}
	
	public void makeAnAppointment(int year, int month, int day, double amount)
	{
		if(isSuspended)
		{
			System.out.println("Sorry, this account is suspended.");
		}
		else
		{
			String myYear = Integer.toString(year);
			String myMonth; String myDay;
			if(month < 1 || month > 12 || day < 1 || day > 31)
			{
				System.out.println("Wrong input time.");
			}
			else
			{
				if(month >= 1 && month <= 9)
				{
					myMonth = "0" + month;
				}
				else
				{
					myMonth = "" + month;
				}
				
				if(day >= 1 && day <= 9)
				{
					myDay = "0" + day;
				}
				else
				{
					myDay = "" + day;
				}
				String totalTime = myYear + "-" + myMonth + "-" + myDay;
				if(appointment.containsKey(totalTime))
				{
					Double value = amount + appointment.get(totalTime);
					appointment.put(totalTime, value);
				}
				else
				{
					Double value = amount;
					appointment.put(totalTime, value);
				}
			}
		}
		
	}
	
	public void withdraw(double amount) // This function is only used to withdraw money from account.
	{
		if(isSuspended)
		{
			System.out.println("Sorry, this account is suspended.");
		}
		else
		{
			if(this.balance - amount < 0)
			{
				System.out.println("For the account "+ accNum+" , we can't "+ "withdraw "+amount+" because it will cause overdraft.");
			}
			else
			{
				this.balance = this.balance - amount;
			}
		}
	}
	
	public void checkAppointments() // This function is used to clear all the appointments
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
			String nowTime[] = nowTimeStr.split("-");
			for(String time: appointment.keySet())
			{
				String timedetail[] = time.split("-");
				if((nowTime[0].equals(timedetail[0]) && nowTime[1].equals(timedetail[1]) &&
						Integer.parseInt(nowTime[2]) - Integer.parseInt(timedetail[2]) >= time_interval)
						|| (Integer.parseInt(nowTime[0]) > Integer.parseInt(timedetail[0]))
						|| (nowTime[0].equals(timedetail[0]) &&	Integer.parseInt(nowTime[1]) > Integer.parseInt(timedetail[1])))
				{
					if(appointment.get(time) > balance)
					{
						System.out.println("The reservation amount is bigger than account balance.");
					}
					else
					{
						this.withdraw(appointment.get(time));
						Double value = 0.0;
						appointment.put(time, value);
					}
				}
			}
			for(String time: appointment.keySet())
			{
				if(appointment.get(time) == 0)
				{
					appointment.remove(time);
				}
			}
		}
	}
}
