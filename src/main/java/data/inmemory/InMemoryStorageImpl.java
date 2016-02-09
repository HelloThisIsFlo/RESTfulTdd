package data.inmemory;

import data.Storage;
import data.Transaction;
import data.TransactionNotSavedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Storage in Memory
 */
public class InMemoryStorageImpl implements Storage {

    Map<Long, Transaction> transactions = new HashMap<>();

    @Override
    public void save(Transaction data, long transactionId) throws TransactionNotSavedException {
        if (data == null || transactionId == 0) {
            throw new TransactionNotSavedException("Transaction == null");
        }
        transactions.put(transactionId, data);
    }

    @Override
    public boolean isPresent(long transactionId) {
        return transactions.containsKey(transactionId);
    }

    @Override
    public Transaction get(long transactionId) {
        return transactions.get(transactionId);
    }
}
