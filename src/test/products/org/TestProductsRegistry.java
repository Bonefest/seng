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
    public void RegisterProduct_WithSameType_ShouldReturnFalse() {
    	assertEquals(registry.registerProduct("TestType", dummyProduct), true);
    	assertEquals(registry.registerProduct("TestType", dummyProduct), false);
    }
    
    @Test
    public void RegisterProduct_WithSameHexType_ShouldReturnFalse() {
    	assertEquals(registry.registerProduct(255, dummyProduct), true);
    	assertEquals(registry.registerProduct("FF", dummyProduct), false);
    }
    
    @Test
    public void RegisterProduct_WithUnexistingType_ShouldReturnFalse() {
    	assertNull(registry.generateProduct("some weird type"));
    }
    
    @Test
    public void RegisterProduct_WithExistingType_ShouldReturnNotNull() {
    	registry.registerProduct("type", dummyProduct);
    	assertNotNull(registry.generateProduct("type"));
    }
    
    @Test
    public void DegenerateProduct_WithNonExistingNumber_ShouldThrowUndefinedProductIDException() {
    	try {
    		registry.degenerateProduct(1);
    		fail();
    	}
    	catch(UndefinedProductIDException exception) {
    		
    	}
    }
    
    @Test
    public void DegenerateProduct_WithExistingNumber_ShouldNotThrowUndefinedProductIDException() {
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
    public void GetNumberOfProductsWithType_WithExistingType_ShouldReturnValueGreaterThanZero() {
    	registry.registerProduct("type", dummyProduct);
    	registry.generateProduct("type");
    	
    	assertEquals(registry.getNumberOfProductsWithType("type"), new Integer(1));
    	assertEquals(registry.getProductsWithType("type").size(), 1);
    }
    
    @Test
    public void GetNumberOfProductsWithType_WithNonExistingType_ShouldReturnMinusOne() {
    	registry.registerProduct("type", dummyProduct);
    	registry.generateProduct("type");
    	
    	assertEquals(registry.getNumberOfProductsWithType("unexisting type"), new Integer(-1));
    	assertNull(registry.getProductsWithType("unexisting type"));
    }
    
    
}
