package src;

import src.main.products.org.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.mockito.Mockito.*;

class Entry {
	
	private static StringBuilder    m_descriptionBuilder;
	private static ProductsRegistry m_registry;
	private static Random           m_random;
	private static Shop             m_shop;
	
	private static void initializeShop() {
		m_descriptionBuilder = new StringBuilder();
		m_random = new Random();
		
		m_shop = new Shop();
		m_shop.setLogger(new ConsoleLogger());
		m_shop.addService(new Cashier());

		m_registry = new ProductsRegistry();
        m_registry.registerProduct(4598, new ProductData("Carrot (limited)",
        						                           "It's a limited carrot",
                								           100.0f,
                								           1.0f,
                								           1.4f));

        m_registry.registerProduct("OX11", new ProductData("Old potato",
                                                           "It's an old potato",
                                                           3.0f,
                                                           0.5f,
                                                           1.3f));

        m_registry.registerProduct("S7C7", new ProductData("Spicy Ketchup",
        		                                           "It's an extremely spicy ketchup",
                                                           100.0f,
                                                           0.7f,
                                                           51.0f));

	}
	
    private static void generateProductDescription(Product product) {
    	m_descriptionBuilder.setLength(0);
    	m_descriptionBuilder.append("[").append(product.getID()).append("]").append(":")
    						.append(product.getName()).append(" - ").append(product.getDescription())
    						.append("(").append(product.getPrice()).append(", ").append(product.getVolume())
    						.append(", ").append(product.getVolume()).append(")");
    }

    private static void renderProducts(ArrayList<Product> products) {
        Iterator prodIt = products.iterator();
        while(prodIt.hasNext()) {
            generateProductDescription((Product)prodIt.next());
            System.out.println(m_descriptionBuilder);
        }

        System.out.println("");
    }

    private static ArrayList<Product> generateProducts(String[] ids,
                                                       int size) {
        ArrayList<Product> result = new ArrayList<Product>();
        for(int i = 0; i < size; ++i) {
            result.add(m_registry.generateProduct(ids[m_random.nextInt(ids.length)]));
        }

        return result;
    }

    
    public static void main(String[] args) {
    	
    	initializeShop();
    	
    	if(m_random.nextBoolean() && m_random.nextBoolean()) {
    		try {
    			Integer randomID = m_random.nextInt(6);
        		System.out.println("Attempt to delete a product with ID " + randomID);
        		m_registry.degenerateProduct(randomID);
        		System.out.println("[Success] Product with ID " + randomID + " has been deleted!");
    		}
    		catch(UndefinedProductIDException exception) {
    			System.out.println(exception.getMessage());
    		}
    	}
    	

    }


}
