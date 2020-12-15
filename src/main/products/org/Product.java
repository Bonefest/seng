package main.products.org;


public class Product {

    private ProductData m_data;
    private Integer     m_id;

    public Product(ProductData data, Integer id) {
        m_data = data;
        m_id = id;
    }

    // NOTE(mizofix): Getters to prevent undesired modification
    // of common data

    public String getName() {
        return m_data.getName();
    }

    public String getDescription() {
        return m_data.getDescription();
    }

    public Float  getPrice() {
        return m_data.getPrice();
    }

    public Float  getWeight() {
        return m_data.getWeight();
    }

    public Float  getVolume() {
        return m_data.getVolume();
    }

    public Integer getID() {
        return m_id;
    }

}
