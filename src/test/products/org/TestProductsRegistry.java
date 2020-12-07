package src.test.products.org;

import src.main.products.org.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnit4.class)
public class TestProductsRegistry {
	
	private ProductsRegistry registry;
	private ProductData      dummyProduct;
	
	@Before
	public void initializeTest() {
		registry = new ProductsRegistry();
		dummyProduct = new ProductData("name", "description", 5.0f, 0.0f, 0.0f);
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
    
    @Test
    public void calculateTotalSumOfProductsWithType_WithProducts_ShouldReturnValueGreaterThanZero() {
    	registry.registerProduct("some product", dummyProduct);
    	registry.generateProduct("some product");
    	registry.generateProduct("some product");
    	
    	assertEquals(new Double(registry.calculateTotalSumOfProductsWithType("some product")), new Double(10.0));
    }
    
    @Test
    public void calculateMostValuedProduct_WithDifferentProducts_ShouldReturnProductB() {
    	
    	ProductData highPricedProduct = new ProductData("product b", "", 1000.0f, 10.0f, 10.0f);
    	
    	registry.registerProduct("product a", dummyProduct);
    	registry.registerProduct("product b", highPricedProduct);
    	
    	registry.generateProduct("product a");
    	registry.generateProduct("product b");
    	
    	Product mostValued = registry.calculateMostValuedProduct();
    	
    	assertEquals(mostValued.getName(), "product b");
    }
    
    @Test
    public void calculateAveragePrice_WithProducts_ShouldReturnValueGreaterThanZero() {
    	
    	ProductData highPricedProduct = new ProductData("product b", "", 20.0f, 10.0f, 10.0f);
    	
    	registry.registerProduct("product a", dummyProduct);
    	registry.registerProduct("product b", highPricedProduct);
    	
    	registry.generateProduct("product a");
    	registry.generateProduct("product a");
    	registry.generateProduct("product a");
    	registry.generateProduct("product b");
    	registry.generateProduct("product b");
    	
    	Double averagePrice = registry.calculateAveragePrice();
    	
    	assertEquals(averagePrice, new Double(11.0));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void findAllSuitableProducts_WithTwoDifferentProducts_ShouldReturnTwoDistictArrays() {
    	
    	ProductData highPricedProduct = new ProductData("product b", "", 20.0f, 10.0f, 10.0f);
    	
    	registry.registerProduct("product a", dummyProduct);
    	registry.registerProduct("product b", highPricedProduct);
    	
    	registry.generateProduct("product a");
    	registry.generateProduct("product b");
    	
    	Object[] pair = registry.findAllSuitableProducts(element -> element.getPrice() > 10.0f);
    	
    	assertEquals(pair.length, 2);
    	List<Product> suitable = (List<Product>)(pair[0]);
    	List<Product> not_suitable = (List<Product>)(pair[0]);
    	
    	assertEquals(suitable.size(), 1);
    	assertEquals(not_suitable.size(), 1);
    	
    }
    
}
