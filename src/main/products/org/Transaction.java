package main.products.org;

import java.util.ArrayList;

public class Transaction {
    public Account            account;
    public ArrayList<Product> products;
    public TransactionType    type;
    
    @Override
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
    
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + account.hashCode();
    	result = 31 * result + products.hashCode();
    	result = 31 * result + type.hashCode();
    	
    	return result;
    }
}
