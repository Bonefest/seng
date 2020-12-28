package main.products.org;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


	private ArrayList<Transaction>     transactionHistory;
    private ArrayList<Service>         services;
    private ProductsRegistry           registry;
    private TransactionLoggerInterface logger;

    private Service getAvailableService(TransactionType type) {
        for(Service service : services) {
            if(service.isSupport(type)) {
                return service;
            }
        }

        return null;
    }
    
    private boolean isTransactionValid(Transaction transaction) {
    	for(Transaction previousTransaction : transactionHistory) {
    		// NOTE(mizofix): If given transaction already happend - something
    		// went wrong!
    		if(transaction.equals(previousTransaction)) {
    			return false;
    		}
    	}
    	
    	return true;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public Shop() {


        transactionHistory = new ArrayList<>();
        services = new ArrayList<>();
        // NOTE(mizofix): We need another responsible class to manage and initialize
        // products registry and our system in general
        registry = new ProductsRegistry();
        logger = new NullLogger();
    }

    public boolean purchase(Transaction transaction) {
    	if(!isTransactionValid(transaction)) {
    		logger.log(String.format("Attempt to execute same transaction (account %d) twice!",
                                     transaction.account.getGUID()));
    		
    		return false;
    	}
    	
        Service service = getAvailableService(transaction.type);
        if(service != null) {
            logger.log(transaction);
            PurchaseStatus status = service.purchase(transaction);
            if(status == PurchaseStatus.ACCEPTED) {
            	
            	transactionHistory.add(transaction);
            	
            	if(transaction.type.allowsDiscount()) {
            		logger.log(String.format("Transaction (account %d) has additional discount!",
                                             transaction.account.getGUID()));
            	}
            	
                Float totalSum = 0.0f;
                for(Product product: transaction.products) {
                	if(transaction.type.allowsDiscount()) {
                		totalSum += product.getPrice() * 0.8f;
                	}
                }

                transaction.account.setBalance(transaction.account.getBalance() - totalSum);

                logger.log(String.format("User [guid = %d] has bought products for %.1f $",
                                           transaction.account.getGUID(), totalSum));

            } else {
                logger.log("Transaction has been rejected due to invalid data or insufficient funds.");
                return false;
            }

            logger.log(String.format("Service [%s] finished with status [%s]",
                                     transaction.type.name(), status.name()));
        } else {
            logger.log("Cannot find acceptable service.");
            return false;
        }
        
        return true;
    }

    public void setLogger(TransactionLoggerInterface inLogger) {
        logger = inLogger;
        logger.onConnect();
    }
    
    public Product findMostCommonProduct(List<Transaction> transactions) {
    	return transactions.stream().
    		flatMap(transaction -> transaction.products.stream()).
    		sorted(((productA, productB) -> productA.getName().compareTo(productB.getName()))).collect(Collectors.toList()).get(0);
    			
    }
    
    public ProductsRegistry getRegistry() {
        return registry;
    }
    
    public void addService(Service service) {
    	services.add(service);
    }

}
