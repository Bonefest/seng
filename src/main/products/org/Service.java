package main.products.org;

import java.util.ArrayList;

public abstract class Service {

    // NOTE(mizofix): Executes specific actions and returns info
    protected abstract PurchaseInfo purchaseImpl(Transaction transaction);

    protected class PurchaseInfo {
        public ArrayList<Product> succeededProducts;
        public ArrayList<Product> rejectedProducts;
        public PurchaseStatus     status;
    }

    public PurchaseStatus purchase(Transaction transaction) {
        // Activates its implementation
        PurchaseInfo purchaseInfo = purchaseImpl(transaction);
        return purchaseInfo.status;
    }

    public boolean isSupport(TransactionType type) { return false; }

}
