package PrivateLab;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrentAccountTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		CurrentAccount myaccount = new CurrentAccount(100, 1000, 20, false, 300);
		myaccount.depositCash(80);
		myaccount.withdraw(300);
		assertTrue(myaccount.balance == -200);
		myaccount.withdraw(100);
		assertTrue(myaccount.balance == -300);
		myaccount.withdraw(0.01);
		assertTrue(myaccount.balance == -300);
	}

}
