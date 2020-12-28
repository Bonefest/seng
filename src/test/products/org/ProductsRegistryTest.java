package test.products.org;

import main.products.org.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;


@RunWith(JUnit4.class)
public class ProductsRegistryTest {
	
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
    	
    	assertEquals(registry.getNumberOfProductsWithType("type"), (int)1);
    	assertEquals(registry.getProductsWithType("type").size(), 1);
    }
    
    @Test
    public void GetNumberOfProductsWithType_WithNonExistingType_ShouldReturnMinusOne() {
    	registry.registerProduct("type", dummyProduct);
    	registry.generateProduct("type");
    	
    	assertEquals(registry.getNumberOfProductsWithType("unexisting type"), (int)-1);
    	assertNull(registry.getProductsWithType("unexisting type"));
    }
    
    @Test
    public void calculateTotalSumOfProductsWithType_WithProducts_ShouldReturnValueGreaterThanZero() {
        String typeName = "some product";

        registry.registerProduct(typeName, dummyProduct);
    	registry.generateProduct(typeName);
    	registry.generateProduct(typeName);
    	
    	assertEquals((double)registry.calculateTotalSumOfProductsWithType(typeName), 10.0);
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
    	
    	Map<Boolean, List<Product>> pair = registry.findAllSuitableProducts(element -> element.getPrice() > 10.0f);
    	
    	List<Product> suitable = pair.get(true);
    	List<Product> not_suitable = pair.get(false);
    	
    	assertEquals(suitable.size(), 1);
    	assertEquals(not_suitable.size(), 1);
    	
    }
    
}
