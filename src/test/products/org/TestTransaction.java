package test.products.org;

import main.products.org.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestTransaction {

    @Test
    public void Equals_WithOnlyDifferentTransactionTypes_ShouldReturnTrue() {
    	Account account = new Account(10.0f, 1);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.Authorative;
    	
    	Transaction transactionB = new Transaction();
    	transactionB.account = account;
    	transactionB.type = TransactionType.Internet;
    	
    	assertTrue(transactionA.equals(transactionB));
    }
    
    @Test
    public void Equals_WithDifferentAccounts_ShouldReturnFalse() {
    	Account account = new Account(10.0f, 1);
    	Account account2 = new Account(0.0f, 2);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.Authorative;
    	
    	Transaction transactionB = new Transaction();
    	transactionB.account = account2;
    	transactionB.type = TransactionType.Authorative;
    	
    	assertFalse(transactionA.equals(transactionB));
    }
    
    @Test
    public void Equals_WithDifferentObjects_ShouldReturnFalse() {
    	Account account = new Account(10.0f, 1);
    	
    	Transaction transactionA = new Transaction();
    	transactionA.account = account;
    	transactionA.type = TransactionType.Authorative;
    	
    	assertFalse(transactionA.equals(account));
    }
}
