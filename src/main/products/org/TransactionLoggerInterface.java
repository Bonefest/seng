package src.main.products.org;

public interface TransactionLoggerInterface {

    public void log(Transaction transaction);
    public void log(String message);

    default public void onConnect() { }

}
