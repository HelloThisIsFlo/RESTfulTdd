package data.inmemory;

import data.Transaction;

public class TransactionWithId {

    public final long transactionId;
    public final Transaction transaction;

    public TransactionWithId(long transactionId, Transaction transaction) {
        this.transactionId = transactionId;
        this.transaction = transaction;
    }
}
