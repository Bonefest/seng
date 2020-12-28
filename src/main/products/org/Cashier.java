package main.products.org;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Cashier extends Service {

    static Logger log = Logger.getLogger(Cashier.class);

	public boolean isProductLimited(Product product) {
		// NOTE(mizofix): Simply imagine that a cashier "looks" at the
		// given product and determines whether it limited or not
		
		String[] words = product.getDescription().split(" ");
		
		for(String word: words) {
			if(word.equalsIgnoreCase("limited")) {
				return true;
			}
		}
		
		return false;
	}
	
    public Service.PurchaseInfo purchaseImpl(Transaction transaction) {

        Service.PurchaseInfo info = new Service.PurchaseInfo();
        info.succeededProducts = new ArrayList<Product>();
        info.rejectedProducts = new ArrayList<Product>();

        Float totalSum = 0.0f;

        for(Product product : transaction.products) {
        	
        	if(isProductLimited(product) &&
        	   product.getPrice() * 2.0 + totalSum > transaction.account.getBalance()) {
        		log.debug("Cahiser says:\n" +
                     "\"This product is limited, so price is doubled! " +
                     "Sorry, but you don't have enough money.\"" +
                     "(You only have " + transaction.account.getBalance() + "$)");
        		
                info.rejectedProducts.add(product);
        	}
        	else if(product.getVolume() > 50.0f) {
                log.debug("Cashier has rejected product \"%s\" cause it's too big!\n" +
                          product.getName());

                info.rejectedProducts.add(product);
            }
            else {
                info.succeededProducts.add(product);
                totalSum += product.getPrice();
            }

        }

        if(info.rejectedProducts.size() > 5 ||
           info.succeededProducts.size() == 0 ||
           totalSum > transaction.account.getBalance()) {
            info.status = PurchaseStatus.REJECTED;
        } else {
            info.status = PurchaseStatus.ACCEPTED;
        }

        return info;
    }

    public boolean isSupport(TransactionType type) {
        return type == TransactionType.AUTHORATIVE;
    }

}
