package src.test.products.org;

import src.main.products.org.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class TestShop {

    private Shop shop = new Shop();
    private ProductsRegistry registry;
    private Transaction transaction;
    
    @Before
    public void setUp() {
    	registry = shop.getRegistry();
    	
    	ProductData data = new ProductData("Product", "", 10.0f, 10.0f, 10.0f);
    	registry.registerProduct("product", data);
    	
    	//shop.addService(new Cashier());
    	
    	transaction = new Transaction();
    	transaction.type = TransactionType.Authorative;
    	transaction.account = new Account(0.0f, 1);
    	transaction.products = new ArrayList<Product>() { {add(registry.generateProduct("product")); } };
    	
    }

    @Test
    public void testShopWithUnspecifiedTransactions() {

    	Transaction transaction = new Transaction();
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.purchase(transaction)).thenReturn(PurchaseStatus.Rejected);
    	
    	shop.addService(mockedCashier);
    	
    	assertEquals(false, shop.purchase(transaction));
    	
    	verify(mockedCashier, never()).purchase(any(Transaction.class));
    }

    @Test
    public void testShopWithNonAvailableTransaction() {
    	Transaction transaction = new Transaction();
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.Internet)).thenReturn(false);
    	
    	shop.addService(mockedCashier);
    	
    	assertEquals(false, shop.purchase(transaction));
    }

    @Test
    public void testShopWithSameTransaction() {
    	transaction.account.setBalance(1000.0f);
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.Authorative)).thenReturn(true);
    	when(mockedCashier.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);
    	shop.addService(mockedCashier);
    	
    	assertEquals(shop.purchase(transaction), true);
    	assertEquals(shop.purchase(transaction), false);
    	
    	verify(mockedCashier).isSupport(TransactionType.Authorative);
    }
    
    @Test
    public void testShopWithIssuedBalanceValue() {
    	transaction.account.setBalance(0.0f);
    	assertEquals(false, shop.purchase(transaction));
    }
 
    @Test
    public void testShopWithNormalBalanceValue() {
    	transaction.account.setBalance(1000.0f);
    	
    	Cashier mockedCahiser = mock(Cashier.class);
    	when(mockedCahiser.isSupport(TransactionType.Authorative)).thenReturn(true);
    	when(mockedCahiser.isSupport(TransactionType.Internet)).thenReturn(true);
    	when(mockedCahiser.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);
    	
    	shop.addService(mockedCahiser);
    	
    	assertEquals(true, shop.purchase(transaction));
    	
    	verify(mockedCahiser).isSupport(TransactionType.Authorative);
    	verify(mockedCahiser).purchase(any(Transaction.class));
    }
    
    @Test
    public void testShopWithSubnormalTransactionType() {
    	Transaction transaction = new Transaction();
    	transaction.type = TransactionType.SeflCheckout;
    	transaction.account = new Account(1000.0f, 1);
    	transaction.products = new ArrayList<Product>() { {add(registry.generateProduct("product")); } };
    	
    	Service mockedService = mock(Service.class);
    	when(mockedService.isSupport(TransactionType.SeflCheckout)).thenReturn(true);
    	when(mockedService.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);
    	
    	shop.addService(mockedService);
    	
    	assertEquals(true, shop.purchase(transaction));
    	
    	verify(mockedService).isSupport(any(TransactionType.class));
    }
    
    @Test
    public void testShopServiceMethodsCalled() {
    	Transaction transaction = new Transaction();
    	transaction.type = TransactionType.SeflCheckout;
    	transaction.account = new Account(1000.0f, 1);
    	transaction.products = new ArrayList<Product>() { {add(registry.generateProduct("product")); } };
    	
    	Service mockedService = mock(Service.class);
    	when(mockedService.isSupport(TransactionType.SeflCheckout)).thenReturn(true);
    	when(mockedService.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);
    	
    	shop.addService(mockedService);
    	
    	shop.purchase(transaction);
    	
    	verify(mockedService).isSupport(TransactionType.SeflCheckout);
    }
    
        
    
    @Test
    public void testRegistryGeneratedSuccessfully() {
    	assertNotNull(shop.getRegistry());
    }
    
    
}
