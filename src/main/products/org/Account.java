package main.products.org;

public class Account {

    private Float   balance;
    private Integer guid;

    public Account(Float startBalance, Integer inGuid) {
        balance = startBalance;
        guid = inGuid;
    }

    public Float getBalance() {
        return balance;
    }

    public Integer getGUID() {
        return guid;
    }

    public void setBalance(Float value) {
        balance = value;
    }


}
