package data;


import java.util.List;

public interface Storage {

    void save(Transaction data, long transactionId) throws TransactionNotSavedException;
    boolean isPresent(long transactionId);
    Transaction get(long transactionId) throws TransactionNotFoundException;
    List<Long> getFromType(String type);
}
