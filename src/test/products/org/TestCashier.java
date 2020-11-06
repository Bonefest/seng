package src.test.products.org;

import src.main.products.org.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class TestCashier {
	
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
		transaction.type = TransactionType.Authorative;
	}
	
    @Test
    public void TestNormalPurchase() {
    	transaction.account.setBalance(100.0f);
    	transaction.products.add(registry.generateProduct("type"));
    	assertEquals(shop.purchase(transaction), true);
    }
    
    @Test
    public void TestAbnormalPurchase() {
    	transaction.products.add(registry.generateProduct("type"));
    	assertEquals(shop.purchase(transaction), false);
    }
    
    @Test
    public void TestLimitedPurchase() {
    	
    	ProductData limitedProduct = new ProductData("limited", "some limited product", 2.0f, 0.0f, 0.0f);
    	
    	registry.registerProduct("limited", limitedProduct);
    	
    	transaction.account.setBalance(2.0f);
    	transaction.products.add(registry.generateProduct("limited"));
    	
    	assertEquals(shop.purchase(transaction), false);
    	
    	transaction.account.setBalance(4.0f);
    	
    	assertEquals(shop.purchase(transaction), true);
    }
    
}
