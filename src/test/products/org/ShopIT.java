package test.products.org;

import main.products.org.*;
import java.util.ArrayList;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ShopIT {

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
    	transaction.type = TransactionType.AUTHORATIVE;
    	transaction.account = new Account(0.0f, 1);
    	transaction.products = new ArrayList<Product>();
    	transaction.products.add(registry.generateProduct("product"));
    	
    }

    @Test
    public void Purchase_WithRejectedTransaction_ShouldReturnFalse() {

    	Transaction transaction = new Transaction();
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.purchase(transaction)).thenReturn(PurchaseStatus.REJECTED);
    	
    	shop.addService(mockedCashier);
    	
    	assertEquals(false, shop.purchase(transaction));
    	
    	verify(mockedCashier, never()).purchase(any(Transaction.class));
    }

    @Test
    public void Purchase_WithSingleInternetTransaction_ShouldReturnFalse() {
    	Transaction ltransaction = new Transaction();
    	
    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.INTERNET)).thenReturn(false);
    	
    	shop.addService(mockedCashier);
    	
    	assertEquals(false, shop.purchase(ltransaction));

    	verify(mockedCashier).isSupport(null);
    }

    @Test
    public void Purchase_WithRepeatedTransaction_ShouldReturnTrueFalse() {
    	transaction.account.setBalance(1000.0f);
    	
    	Cashier spiedCashier = spy(Cashier.class);
    	when(spiedCashier.isSupport(TransactionType.AUTHORATIVE)).thenReturn(true);
    	shop.addService(spiedCashier);
    	
    	assertEquals(shop.purchase(transaction), true);
    	assertEquals(shop.purchase(transaction), false);
    	
    	verify(spiedCashier).isSupport(TransactionType.AUTHORATIVE);
    	verify(spiedCashier).purchase(any(Transaction.class));
    }
    
    @Test
    public void Purchase_WithEmptyBalance_ShouldReturnFalse() {
    	transaction.account.setBalance(0.0f);

    	Cashier mockedCashier = mock(Cashier.class);
    	when(mockedCashier.isSupport(TransactionType.AUTHORATIVE)).thenReturn(true);

    	shop.addService(mockedCashier);

    	assertEquals(false, shop.purchase(transaction));

    	verify(mockedCashier).isSupport(TransactionType.AUTHORATIVE);
    }
 
    @Test
    public void Purchase_WithNormalBalance_ShouldReturnTrue() {
    	transaction.account.setBalance(1000.0f);
    	
    	Cashier spiedCahiser = spy(Cashier.class);
    	when(spiedCahiser.isSupport(TransactionType.AUTHORATIVE)).thenReturn(true);
    	when(spiedCahiser.isSupport(TransactionType.INTERNET)).thenReturn(true);
    	
    	shop.addService(spiedCahiser);
    	
    	assertEquals(true, shop.purchase(transaction));
    	
    	verify(spiedCahiser).isSupport(TransactionType.AUTHORATIVE);
    	verify(spiedCahiser).purchase(any(Transaction.class));
    }
    
    @Test
    public void Purchase_WithSubnormalTransactionType_ShouldReturnTrue() {
    	Transaction transaction = new Transaction();
    	transaction.type = TransactionType.SELF_CHECKOUT;
    	transaction.account = new Account(1000.0f, 1);
    	transaction.products = new ArrayList<Product>();
    	transaction.products.add(registry.generateProduct("product"));
    	
    	Service mockedService = mock(Service.class);
    	when(mockedService.isSupport(TransactionType.SELF_CHECKOUT)).thenReturn(true);
    	when(mockedService.purchase(transaction)).thenReturn(PurchaseStatus.ACCEPTED);
    	
    	shop.addService(mockedService);
    	
    	assertEquals(true, shop.purchase(transaction));
    	
	verify(mockedService).purchase(any(Transaction.class));
    	verify(mockedService).isSupport(any(TransactionType.class));
    }
    
    @Test
    public void Purchase_WithSingleNormalProduct_ShouldReturnTrue() {
    	Transaction ltransaction = new Transaction();
    	ltransaction.type = TransactionType.SELF_CHECKOUT;
    	ltransaction.account = new Account(1000.0f, 1);
    	ltransaction.products = new ArrayList<Product>();
    	ltransaction.products.add(registry.generateProduct("product"));
    	
    	Service mockedService = mock(Service.class);
    	when(mockedService.isSupport(TransactionType.SELF_CHECKOUT)).thenReturn(true);
    	when(mockedService.purchase(ltransaction)).thenReturn(PurchaseStatus.ACCEPTED);

    	shop.addService(mockedService);
    	assertEquals(true, shop.purchase(ltransaction));
 
    	verify(mockedService).isSupport(TransactionType.SELF_CHECKOUT);
    	verify(mockedService).purchase(any(Transaction.class));
    }
    

    
}
