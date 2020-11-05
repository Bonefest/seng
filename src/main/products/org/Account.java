package src.main.products.org;

public class Account {

    private Float   m_balance;
    private Integer m_guid;

    public Account(Float startBalance, Integer guid) {
        m_balance = startBalance;
        m_guid = guid;
    }

    public Float getBalance() {
        return m_balance;
    }

    public Integer getGUID() {
        return m_guid;
    }

    public void setBalance(Float value) {
        m_balance = value;
    }


}
