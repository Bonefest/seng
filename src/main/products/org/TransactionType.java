package src.main.products.org;

public enum TransactionType {
    Authorative(true, 0),
    SeflCheckout(false, 1),
    Internet(false, 2);
    
	boolean requiresCashier;
	int     securityLevel;
	
    TransactionType(boolean requiresCashier, int secutiryLevel) {
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
