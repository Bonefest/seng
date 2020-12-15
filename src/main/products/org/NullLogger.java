package main.products.org;

public class NullLogger implements TransactionLoggerInterface {

    public void log(Transaction transaction) {
        // NOTE(mizofix): pass
    }

    public void log(String message) {
        // NOTE(mizofix): pass
    }

}
