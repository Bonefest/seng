package main.products.org;

public class ProductData {

    private String name;
    private String description;
    private Float  price;
    private Float  weight;
    private Float  volume;

    public ProductData(String inName,
                       String inDescription,
                       Float inPrice,
                       Float inWeight,
                       Float inVolume) {
        name = inName;
        description = inDescription;
        price = inPrice;
        weight = inWeight;
        volume = inVolume;
    }


    public void setName(String inName) {
        name = inName;
    }

    public void setDescription(String inDescription) {
        description = inDescription;
    }

    public void setPrice(Float inPrice) {
        price = inPrice;
    }

    public void setWeight(Float inWeight) {
        weight = inWeight;
    }

    public void setVolume(Float inVolume) {
        volume = inVolume;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getVolume() {
        return volume;
    }

}
