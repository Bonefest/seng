package main.products.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.function.*;

import java.util.ArrayList;
import java.util.Collection;

public class ProductsRegistry {

    private HashMap<String, ProductData>        registry;
    private HashMap<String, ArrayList<Product>> products;

    private Integer idCounter;

    public ProductsRegistry() {
        idCounter = 0;

        registry = new HashMap<>();
        products = new HashMap<>();
    }
    
    public boolean registerProduct(Integer hexType, ProductData data) {
    	return registerProduct(Integer.toHexString(hexType), data);
    }

    public boolean registerProduct(String type, ProductData data) {
        if(registry.containsKey(type)) {
            return false;
        }
        
        for(ProductData registryData : registry.values()) {
        	// NOTE(mizofix): Attempt to register same product type with several
        	// names is incorrect!
        	if(data.equals(registryData)) {
        		return false;
        	}
        }

        registry.put(type, data);
        return true;
    }

    public boolean unregisterProduct(String name) {
        return (registry.remove(name) != null);
    }
    
    public Product generateProduct(String type) {
        ProductData data = registry.get(type);
        if(data != null) {
            Product product = new Product(data, idCounter++);
            ArrayList<Product> list = products.get(type);
            if(list == null) {
                list = new ArrayList<>();
                products.put(type, list);
            }

            list.add(product);

            return product;
        }

        return null;
    }

    public void degenerateProduct(Integer id) throws UndefinedProductIDException{
        // WARNING(mizofix): Extremely naive implementation
        for(String type : products.keySet()) {
            for(Product product: products.get(type)) {
                if(product.getID() == id) {
                    products.remove(product);
                    return;
                }
            }
        }

        throw new UndefinedProductIDException("Product ID " + id + " is undefined!");
    }
    
    public List<Product> getAvailableProducts() {
    	ArrayList<Product> result = new ArrayList<>();
    	for(ArrayList<Product> prods: products.values()) {
    		result.addAll(prods);
    	}
    	
    	return result;
    }
    
    public double calculateTotalSumOfProductsWithType(String type) {
    	return getProductsWithType(type).
    			stream().
    			mapToDouble(product -> product.getPrice()).
    			reduce(0, (sum, price)-> sum + price);
    }
    
    public Product calculateMostValuedProduct() {
    	return getAvailableProducts().stream().
    			max((left, right) -> Float.compare(left.getPrice(), right.getPrice())).get();
    }
    
    public Double calculateAveragePrice() {
    	List<Product> prods = getAvailableProducts();
    		
    	return (prods.stream().mapToDouble(product -> product.getPrice()).
                reduce(0, (prevSum, sum) -> prevSum + sum)) / products.size();
    }

    public Map<Boolean, List<Product>> findAllSuitableProducts(Predicate<? super Product> condition) {
    	return getAvailableProducts().stream().collect(Collectors.partitioningBy(condition));
    	
    }
    
    public Collection<String> getAvailableProductTypes() {
    	return registry.keySet();
    }
    
    public List<Product> getProductsWithType(String type) {
        List<Product> prods = products.get(type);
        if(prods != null) {
            prods = new ArrayList<Product>(prods);
        }

        return prods;
    }

    public int getNumberOfProductsWithType(String type) {
        List<Product> prods = products.get(type);
        if(prods != null) {
            return (int)prods.size();
        }

        return (int)-1;
    }

}
