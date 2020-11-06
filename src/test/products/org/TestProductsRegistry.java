package src.test.products.org;

import src.main.products.org.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class TestProductsRegistry {
	
	private ProductsRegistry registry;
	private ProductData      dummyProduct;
	
	@Before
	public void initializeTest() {
		registry = new ProductsRegistry();
		dummyProduct = new ProductData("name", "description", 0.0f, 0.0f, 0.0f);
	}
	
    @Test
    public void testRegisterSameTypeTwice() {
    	assertEquals(registry.registerProduct("TestType", dummyProduct), true);
    	assertEquals(registry.registerProduct("TestType", dummyProduct), false);
    }
    
    @Test
    public void testRegisterHexType() {
    	assertEquals(registry.registerProduct(255, dummyProduct), true);
    	assertEquals(registry.registerProduct("FF", dummyProduct), false);
    }
    
    @Test
    public void testGenerateUnexistingProduct() {
    	assertNull(registry.generateProduct("some weird type"));
    }
    
    @Test
    public void testGenerateExistingProduct() {
    	registry.registerProduct("type", dummyProduct);
    	assertNotNull(registry.generateProduct("type"));
    }
    
    @Test
    public void testDegenerateNonExistingProduct() {
    	try {
    		registry.degenerateProduct(1);
    		fail();
    	}
    	catch(UndefinedProductIDException exception) {
    		
    	}
    }
    
    @Test
    public void testDegenerateExistingProduct() {
    	registry.registerProduct("type", dummyProduct);
    	registry.generateProduct("type");
    	try {
    		registry.degenerateProduct(0);
    	}
    	catch(UndefinedProductIDException exception) {
    		fail();
    	}
    }
    
    @Test
    public void testFindExistingProducts() {
    	registry.registerProduct("type", dummyProduct);
    	registry.generateProduct("type");
    	
    	assertEquals(registry.getNumberOfProductsWithType("type"), new Integer(1));
    	assertEquals(registry.getNumberOfProductsWithType("unexisting type"), new Integer(-1));
    	
    	assertEquals(registry.getProductsWithType("type").size(), 1);
    	assertNull(registry.getProductsWithType("unexisting type"));
    }
}
