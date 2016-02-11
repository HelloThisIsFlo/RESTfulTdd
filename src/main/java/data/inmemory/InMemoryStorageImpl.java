package data.inmemory;

import data.Storage;
import data.Transaction;
import data.TransactionNotFoundException;
import data.TransactionNotSavedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Transaction get(long transactionId) throws TransactionNotFoundException {
        Transaction transaction = transactions.get(transactionId);
        if (transaction != null) {
            return transaction;
        } else {
            throw new TransactionNotFoundException();
        }
    }

    @Override
    public List<Long> getFromType(String type) {
        List<Long> result = new ArrayList<>();
        for (Map.Entry<Long, Transaction> entry : transactions.entrySet()) {
            Transaction transaction = entry.getValue();
            long transactionId = entry.getKey();
            if (isTransactionOfType(transaction, type)) {
                result.add(transactionId);
            }
        }
        return result;
    }

    private boolean isTransactionOfType(Transaction transaction, String type) {
        return type != null && type.equals(transaction.type);
    }
}
