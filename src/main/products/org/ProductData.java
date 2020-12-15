package main.products.org;

public class ProductData {

    private String m_name;
    private String m_description;
    private Float  m_price;
    private Float  m_weight;
    private Float  m_volume;

    public ProductData(String name,
                       String description,
                       Float price,
                       Float weight,
                       Float volume) {
        m_name = name;
        m_description = description;
        m_price = price;
        m_weight = weight;
        m_volume = volume;
    }


    public void setName(String name) {
        m_name = name;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public void setPrice(Float price) {
        m_price = price;
    }

    public void setWeight(Float weight) {
        m_weight = weight;
    }

    public void setVolume(Float volume) {
        m_volume = volume;
    }


    public String getName() {
        return m_name;
    }

    public String getDescription() {
        return m_description;
    }

    public Float getPrice() {
        return m_price;
    }

    public Float getWeight() {
        return m_weight;
    }

    public Float getVolume() {
        return m_volume;
    }

}
