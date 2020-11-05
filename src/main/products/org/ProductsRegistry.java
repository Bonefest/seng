package src.main.products.org;

import java.util.HashMap;
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
