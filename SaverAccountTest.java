package PrivateLab;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SaverAccountTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		SaverAccount myaccount = new SaverAccount(100,1000,20,false);
		myaccount.makeAnAppointment(2023, 4, 23, 200);
		myaccount.checkAppointments();
		assertTrue(myaccount.balance == 20);
		myaccount.depositCash(2000);
		myaccount.makeAnAppointment(2023, 4, 23, 100);
		myaccount.checkAppointments();
		assertTrue(myaccount.balance == 1720);
		myaccount.makeAnAppointment(2023, 6, 23, 100);
		myaccount.checkAppointments();
		assertTrue(myaccount.balance == 1720);
	}

}
