package src.main.products.org;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.internal.util.collections.ListUtil;

public class Shop {


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


	private ArrayList<Transaction>     m_transactionHistory;
    private ArrayList<Service>         m_services;
    private ProductsRegistry           m_registry;
    private TransactionLoggerInterface m_logger;

    private Service getAvailableService(TransactionType type) {
        for(Service service : m_services) {
            if(service.isSupport(type)) {
                return service;
            }
        }

        return null;
    }
    
    private boolean isTransactionValid(Transaction transaction) {
    	for(Transaction previousTransaction : m_transactionHistory) {
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


        m_transactionHistory = new ArrayList<Transaction>();
        m_services = new ArrayList<Service>();
        // NOTE(mizofix): We need another responsible class to manage and initialize
        // products registry and our system in general
        m_registry = new ProductsRegistry();
        m_logger = new NullLogger();
    }

    public boolean purchase(Transaction transaction) {
    	if(!isTransactionValid(transaction)) {
    		m_logger.log(String.format("Attempt to execute same transaction (account %d) twice!",
    				     transaction.account.getGUID()));
    		
    		return false;
    	}
    	
        Service service = getAvailableService(transaction.type);
        if(service != null) {
            m_logger.log(transaction);
            PurchaseStatus status = service.purchase(transaction);
            if(status == PurchaseStatus.Accepted) {
            	
            	m_transactionHistory.add(transaction);
            	
            	if(transaction.type.allowsDiscount()) {
            		m_logger.log(String.format("Transaction (account %d) has additional discount!",
            				                   transaction.account.getGUID()));
            	}
            	
                Float totalSum = 0.0f;
                for(Product product: transaction.products) {
                	if(transaction.type.allowsDiscount()) {
                		totalSum += product.getPrice() * 0.8f;
                	}
                }

                transaction.account.setBalance(transaction.account.getBalance() - totalSum);

                m_logger.log(String.format("User [guid = %d] has bought products for %.1f $",
                                           transaction.account.getGUID(), totalSum));

            } else {
                m_logger.log("Transaction has been rejected due to invalid data or insufficient funds.");
                return false;
            }

            m_logger.log(String.format("Service [%s] finished with status [%s]",
                                       transaction.type.name(), status.name()));
        } else {
            m_logger.log("Cannot find acceptable service.");
            return false;
        }
        
        return true;
    }

    public void setLogger(TransactionLoggerInterface logger) {
        m_logger = logger;
        m_logger.onConnect();
    }
    
    public Product findMostCommonProduct(ArrayList<Transaction> transactions) {
    	return transactions.stream().
    		map(transaction -> transaction.products).
    		reduce(new ArrayList<Product>(), (current, rest) -> new ArrayList<Product>() {{addAll(current); addAll(rest);}}).
    		stream().
    		sorted(((productA, productB) -> productA.getName().compareTo(productB.getName()))).collect(Collectors.toList()).get(0);
    			
    }
    
    public ProductsRegistry getRegistry() {
        return m_registry;
    }
    
    public void addService(Service service) {
    	m_services.add(service);
    }

}
