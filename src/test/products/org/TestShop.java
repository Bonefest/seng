package tests.products.org;

import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import products.org.*;

@RunWith(JUnit4.class)
public class TestShop {

    private Shop shop = new Shop();
    private ProductsRegistry registry;
    private Transaction transaction;
    
    @Before
    public void setUp() {
    	registry = shop.getRegistry();
    	
    	ProductData data = new ProductData("Product", "", 10.0f, 10.0f, 50.0f);
    	registry.registerProduct("product", data);
    	
    	shop.addService(new Cashier());
    	
    	transaction = new Transaction();
    	transaction.type = TransactionType.Authorative;
    	transaction.account = new Account(0.0f, 1);
    	transaction.products = new ArrayList<Product>() { {add(registry.generateProduct("product")); } };
    	
    }

    @Test
    public void testShopWithUnspecifiedTransactions() {
    	Transaction transaction = new Transaction();
    	assertEquals(false, shop.purchase(transaction));
    }

    @Test
    public void testShopWithNonAvailableTransaction() {
    	Transaction transaction = new Transaction();
    	transaction.type = TransactionType.Internet;
    	assertEquals(false, shop.purchase(transaction));
    }

    @Test
    public void testShopWithSameTransaction() {
    	transaction.account.setBalance(1000.0f);
    	assertEquals(shop.purchase(transaction), true);
    	assertEquals(shop.purchase(transaction), false);
    }
    
    @Test
    public void testShopWithIssuedBalanceValue() {
    	transaction.account.setBalance(0.0f);
    	assertEquals(false, shop.purchase(transaction));
    }
 
    @Test
    public void testShopWithNormalBalanceValue() {
    	transaction.account.setBalance(1000.0f);
    	assertEquals(true, shop.purchase(transaction));
    }
    
    @Test
    public void testRegistryGeneratedSuccessfully() {
    	assertNotNull(shop.getRegistry());
    }
    
    
}
