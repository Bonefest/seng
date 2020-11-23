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
    public void Purchase_WithRejectedTransaction_ShouldReturnFalse() {

    	Transaction transaction = new Transaction();
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.purchase(transaction)).thenReturn(PurchaseStatus.Rejected);
    	
    	shop.addService(mockedCashier);
    	
    	assertEquals(false, shop.purchase(transaction));
    	
    	verify(mockedCashier, never()).purchase(any(Transaction.class));
    }

    @Test
    public void Purchase_WithSingleInternetTransaction_ShouldReturnFalse() {
    	Transaction transaction = new Transaction();
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.Internet)).thenReturn(false);
    	
    	shop.addService(mockedCashier);
    	
    	assertEquals(false, shop.purchase(transaction));

	verify(mockedCashier).isSupport(TransactionType.Internet);
    }

    @Test
    public void Purchase_WithRepeatedTransaction_ShouldReturnTrueFalse() {
    	transaction.account.setBalance(1000.0f);
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.Authorative)).thenReturn(true);
    	when(mockedCashier.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);
    	shop.addService(mockedCashier);
    	
    	assertEquals(shop.purchase(transaction), true);
    	assertEquals(shop.purchase(transaction), false);
    	
    	verify(mockedCashier).isSupport(TransactionType.Authorative);
	verify(mockedCashier).purchase(any(Transaction.class));
    }
    
    @Test
    public void Purchase_WithEmptyBalance_ShouldReturnFalse() {
    	transaction.account.setBalance(0.0f);

    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.Authorative)).thenReturn(true);

	shop.addService(mockedCahiser);

    	assertEquals(false, shop.purchase(transaction));

	verify(mockedCashier).isSupport(TransactionType.Authorative);
    }
 
    @Test
    public void Purchase_WithNormalBalance_ShouldReturnTrue() {
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
    public void Purchase_WithSubnormalTransactionType_ShouldReturnTrue() {
    	Transaction transaction = new Transaction();
    	transaction.type = TransactionType.SeflCheckout;
    	transaction.account = new Account(1000.0f, 1);
    	transaction.products = new ArrayList<Product>() { {add(registry.generateProduct("product")); } };
    	
    	Service mockedService = mock(Service.class);
    	when(mockedService.isSupport(TransactionType.SeflCheckout)).thenReturn(true);
    	when(mockedService.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);
    	
    	shop.addService(mockedService);
    	
    	assertEquals(true, shop.purchase(transaction));
    	
	verify(mockedService).purchase(any(Transaction.class));
    	verify(mockedService).isSupport(any(TransactionType.class));
    }
    
    @Test
    public void Purchase_WithSingleNormalProduct_ShouldReturnTrue() {
    	Transaction transaction = new Transaction();
    	transaction.type = TransactionType.SeflCheckout;
    	transaction.account = new Account(1000.0f, 1);
    	transaction.products = new ArrayList<Product>() { {add(registry.generateProduct("product")); } };
    	
    	Service mockedService = mock(Service.class);
    	when(mockedService.isSupport(TransactionType.SeflCheckout)).thenReturn(true);
    	when(mockedService.purchase(transaction)).thenReturn(PurchaseStatus.Accepted);

    	shop.addService(mockedService);
    	assertEquals(true, shop.purchase(transaction));
 
    	verify(mockedService).isSupport(TransactionType.SeflCheckout);
	verify(mockedService).purchase(any(Transaction.class));
    }
    
}
