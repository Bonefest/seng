package main.products.org;

public interface TransactionLoggerInterface {

    public void log(Transaction transaction);
    public void log(String message);

    public default void onConnect() { }

}
