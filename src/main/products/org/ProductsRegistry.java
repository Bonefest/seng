package main.products.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// import com.google.common.base.Predicate;

import java.util.function.*;

import java.util.ArrayList;
import java.util.Collection;

public class ProductsRegistry {

    private HashMap<String, ProductData>        m_registry;
    private HashMap<String, ArrayList<Product>> m_products;

    private Integer m_idCounter;

    public ProductsRegistry() {
        m_idCounter = 0;

        m_registry = new HashMap<String, ProductData>();
        m_products = new HashMap<String, ArrayList<Product>>();
    }
    
    public boolean registerProduct(Integer hexType, ProductData data) {
    	return registerProduct(Integer.toHexString(hexType), data);
    }

    public boolean registerProduct(String type, ProductData data) {
        if(m_registry.containsKey(type)) {
            return false;
        }
        
        for(ProductData registryData : m_registry.values()) {
        	// NOTE(mizofix): Attempt to register same product type with several
        	// names is incorrect!
        	if(data.equals(registryData)) {
        		return false;
        	}
        }

        m_registry.put(type, data);
        return true;
    }

    public boolean unregisterProduct(String name) {
        return (m_registry.remove(name) != null);
    }
    
    public Product generateProduct(String type) {
        ProductData data = m_registry.get(type);
        if(data != null) {
            Product product = new Product(data, m_idCounter++);
            ArrayList<Product> list = m_products.get(type);
            if(list == null) {
                list = new ArrayList<Product>();
                m_products.put(type, list);
            }

            list.add(product);

            return product;
        }

        return null;
    }

    public void degenerateProduct(Integer id) throws UndefinedProductIDException{
        // WARNING(mizofix): Extremely naive implementation
        for(String type : m_products.keySet()) {
            for(Product product: m_products.get(type)) {
                if(product.getID() == id) {
                    m_products.remove(product);
                    return;
                }
            }
        }

        throw new UndefinedProductIDException("Product ID " + id + " is undefined!");
    }
    
    public ArrayList<Product> getAvailableProducts() {
    	ArrayList<Product> result = new ArrayList<Product>();
    	for(ArrayList<Product> products: m_products.values()) {
    		result.addAll(products);
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
    	ArrayList<Product> products = getAvailableProducts();
    		
    	return (products.stream().mapToDouble(product -> product.getPrice()).
    				reduce(0, (prevSum, sum) -> prevSum + sum)) / products.size();
    }

    public Map<Boolean, List<Product>> findAllSuitableProducts(Predicate<? super Product> condition) {
    	return getAvailableProducts().stream().collect(Collectors.partitioningBy(condition));
    	
    }
    
    public Collection<String> getAvailableProductTypes() {
    	return m_registry.keySet();
    }
    
    public ArrayList<Product> getProductsWithType(String type) {
        ArrayList<Product> products = m_products.get(type);
        if(products != null) {
            products = (ArrayList)products.clone();
        }

        return products;
    }

    public Integer getNumberOfProductsWithType(String type) {
        ArrayList<Product> products = m_products.get(type);
        if(products != null) {
            return new Integer(products.size());
        }

        return new Integer(-1);
    }

}
