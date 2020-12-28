
import main.products.org.*;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.mockito.Mockito.*;

class Entry {

    static Logger log = Logger.getLogger(Entry.class.getName());

	private static StringBuilder    descriptionBuilder;
	private static ProductsRegistry registry;
	private static Random           random;
	private static Shop             shop;
	
	private static void initializeShop() {
		descriptionBuilder = new StringBuilder();
		random = new Random();
		
		shop = new Shop();
		shop.setLogger(new ConsoleLogger());
		shop.addService(new Cashier());

		registry = new ProductsRegistry();
        registry.registerProduct(4598, new ProductData("Carrot (limited)",
        						                           "It's a limited carrot",
                								           100.0f,
                								           1.0f,
                								           1.4f));

        registry.registerProduct("OX11", new ProductData("Old potato",
                                                           "It's an old potato",
                                                           3.0f,
                                                           0.5f,
                                                           1.3f));

        registry.registerProduct("S7C7", new ProductData("Spicy Ketchup",
        		                                           "It's an extremely spicy ketchup",
                                                           100.0f,
                                                           0.7f,
                                                           51.0f));

	}
	
    private static void generateProductDescription(Product product) {
    	descriptionBuilder.setLength(0);
    	descriptionBuilder.append("[").append(product.getID()).append("]").append(":")
    						.append(product.getName()).append(" - ").append(product.getDescription())
    						.append("(").append(product.getPrice()).append(", ").append(product.getVolume())
    						.append(", ").append(product.getVolume()).append(")");
    }

    private static void renderProducts(ArrayList<Product> products) {
        Iterator<Product> prodIt = products.iterator();
        while(prodIt.hasNext()) {
            generateProductDescription((Product)prodIt.next());
            log.debug(descriptionBuilder);
        }

    }

    private static ArrayList<Product> generateProducts(String[] ids,
                                                       int size) {
        ArrayList<Product> result = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            result.add(registry.generateProduct(ids[random.nextInt(ids.length)]));
        }

        return result;
    }

    private static Transaction generateDemoTransaction() {
    	Collection<String> productTypes = registry.getAvailableProductTypes();
    	String[] convertedProductTypes = productTypes.toArray(new String[productTypes.size()]);
    	
	    Transaction transaction = new Transaction();
	    transaction.account = new Account(random.nextFloat() * 1000.0f, 1);
	    transaction.products = generateProducts(convertedProductTypes, random.nextInt(9) + 1);
	    transaction.type = TransactionType.AUTHORATIVE;
	    
	    return transaction;
    }
    
    public static void main(String[] args) {
    	
    	initializeShop();
    	Transaction demoTransaction = generateDemoTransaction();
    	
    	if(random.nextInt() % 4 == 0) {
    		try {
    			Integer randomID = random.nextInt(6);
        		log.debug("Attempt to delete a product with ID " + randomID);
        		registry.degenerateProduct(randomID);
        		log.debug("[Success] Product with ID " + randomID + " has been deleted!");
    		}
    		catch(UndefinedProductIDException exception) {
    			log.debug(exception.getMessage());
    		}
    	}
    	
        log.debug("Attempt to buy next products:\n");
        renderProducts(demoTransaction.products);
        shop.purchase(demoTransaction);

    }


}
