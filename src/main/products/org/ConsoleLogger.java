package src.main.products.org;

public class ConsoleLogger implements TransactionLoggerInterface {

    public void log(Transaction transaction) {
        System.out.printf("Transaction [%s] from user [guid = %d]\n",
                          transaction.type,
                          transaction.account.getGUID());
    }

    public void log(String message) {
        System.out.println(message);
    }

}
