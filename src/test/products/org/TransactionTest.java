package test.products.org;

import main.products.org.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TransactionTest {

    @Test
    public void Equals_WithOnlyDifferentTransactionTypes_ShouldReturnTrue() {
    	Account account = new Account(10.0f, 1);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.AUTHORATIVE;
    	
    	Transaction transactionB = new Transaction();
    	transactionB.account = account;
    	transactionB.type = TransactionType.INTERNET;
    	
    	assertTrue(transactionA.equals(transactionB));
    }
    
    @Test
    public void Equals_WithDifferentAccounts_ShouldReturnFalse() {
    	Account account = new Account(10.0f, 1);
    	Account account2 = new Account(0.0f, 2);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.AUTHORATIVE;
    	
    	Transaction transactionB = new Transaction();
    	transactionB.account = account2;
    	transactionB.type = TransactionType.AUTHORATIVE;
    	
    	assertFalse(transactionA.equals(transactionB));
    }
    
    @Test
    public void Equals_WithDifferentObjects_ShouldReturnFalse() {
    	Account account = new Account(10.0f, 1);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.AUTHORATIVE;
    	
    	assertFalse(transactionA.equals(account));
    }
}
