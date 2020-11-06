package src.test.products.org;

import src.main.products.org.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestTransaction {

    @Test
    public void testTransactionComparation() {
    	Account account = new Account(10.0f, 1);
    	Account account2 = new Account(0.0f, 2);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.Authorative;
    	
    	Transaction transactionB = new Transaction();
    	transactionB.account = account;
    	transactionB.type = TransactionType.Internet;
    	
    	Transaction transactionC = new Transaction();
    	transactionC.account = account2;
    	transactionC.type = TransactionType.Authorative;
    	
    	assertTrue(transactionA.equals(transactionB));
    	assertFalse(transactionA.equals(transactionC));
    	assertFalse(transactionA.equals(account));
    }
    
}
