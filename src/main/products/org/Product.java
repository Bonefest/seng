package main.products.org;


public class Product {

    private ProductData data;
    private Integer     id;

    public Product(ProductData inData, Integer inId) {
        data = inData;
        id = inId;
    }

    // NOTE(mizofix): Getters to prevent undesired modification
    // of common data

    public String getName() {
        return data.getName();
    }

    public String getDescription() {
        return data.getDescription();
    }

    public Float  getPrice() {
        return data.getPrice();
    }

    public Float  getWeight() {
        return data.getWeight();
    }

    public Float  getVolume() {
        return data.getVolume();
    }

    public Integer getID() {
        return id;
    }

}
