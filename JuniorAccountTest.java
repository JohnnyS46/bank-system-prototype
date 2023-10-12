package PrivateLab;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JuniorAccountTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		JuniorAccount myaccount = new JuniorAccount(100, 1000, 20, false);
		assertTrue(myaccount.balance == 20);
		assertTrue(myaccount.PIN == 1000);
		assertTrue(myaccount.accNum == 100);
		assertTrue(myaccount.isSuspended == false);
		myaccount.depositCash(300);
		assertTrue(myaccount.balance == 320);
		myaccount.withdraw(80);
		assertTrue(myaccount.balance == 240);
		myaccount.withdraw(80);
		assertTrue(myaccount.balance == 240);
	}

}
