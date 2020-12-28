package main.products.org;

public enum TransactionType {
    AUTHORATIVE(true, 0),
    SELF_CHECKOUT(false, 1),
    INTERNET(false, 2);
    
	boolean requiresCashier;
	int     securityLevel;
	
    TransactionType(boolean requiresCashier, int securityLevel) {
    	this.requiresCashier = requiresCashier;
    	this.securityLevel   = securityLevel;
    }
    
    @Override
    public String toString() {
    	return String.format("%s (cashier: %s) with security level %d",
		    		         super.toString(),
		    		         (requiresCashier ? "required" : "not required"),
		    		         securityLevel);
    	
    }
    
    boolean allowsDiscount() {
    	return requiresCashier;
    }
    
}
