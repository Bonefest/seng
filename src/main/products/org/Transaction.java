package main.products.org;

import java.util.ArrayList;

public class Transaction {
    public Account            account;
    public ArrayList<Product> products;
    public TransactionType    type;
    
    public boolean equals(Object object) {
    	if(object == this) {
    		return true;
    	}
    	
    	if(!(object instanceof Transaction)) {
    		return false;
    	}
    	
    	Transaction transaction = (Transaction) object;
    	return (transaction.account == account) && (transaction.products == products);
    }
}
