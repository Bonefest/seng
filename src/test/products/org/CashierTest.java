package test.products.org;

import main.products.org.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class CashierTest {
	
	private ProductsRegistry registry;
	private ProductData dummyProduct;
	private Cashier cashier;
	private Shop shop;
	
	private Transaction transaction;
	
	@Before
	public void initializeTests() {
		registry = new ProductsRegistry();
		dummyProduct = new ProductData("name", "description", 1.0f, 0.0f, 0.0f);
		registry.registerProduct("type", dummyProduct);
		
		cashier  = new Cashier();
		shop = new Shop();
		shop.addService(cashier);
		
		transaction = new Transaction();
		transaction.account = new Account(0.0f, 0);
		transaction.products = new ArrayList<Product>();
		transaction.type = TransactionType.AUTHORATIVE;
	}
	
    @Test
    public void Purchase_WithExistingNormalCashier_ShouldReturnTrue() {
    	transaction.account.setBalance(100.0f);
    	transaction.products.add(registry.generateProduct("type"));
    	assertEquals(shop.purchase(transaction), true);
    }
    
    @Test
    public void Purchase_WithExistingSubnormalCashier_ShouldReturnFalse() {
    	transaction.products.add(registry.generateProduct("type"));
    	assertEquals(shop.purchase(transaction), false);
    }
    
    @Test
    public void Purchase_WithLimitedBalance_ShouldReturnFalse() {
    	
    	ProductData limitedProduct = new ProductData("limited", "some limited product", 2.0f, 0.0f, 0.0f);
    	
    	registry.registerProduct("limited", limitedProduct);
    	
    	transaction.account.setBalance(2.0f);
    	transaction.products.add(registry.generateProduct("limited"));
    	
    	assertEquals(shop.purchase(transaction), false);
    }
    
    @Test
    public void Purchase_WithNormalBalance_ShouldReturnTrue() {
    	
    	ProductData limitedProduct = new ProductData("limited", "some limited product", 2.0f, 0.0f, 0.0f);
    	
    	registry.registerProduct("limited", limitedProduct);
    	transaction.products.add(registry.generateProduct("limited"));
    	
    	transaction.account.setBalance(4.0f);
    	
    	assertEquals(shop.purchase(transaction), true);
    }
    
    
}
