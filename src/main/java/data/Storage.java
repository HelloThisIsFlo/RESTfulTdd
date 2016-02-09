package data;


public interface Storage {

    void save(Transaction data, long transactionId) throws TransactionNotSavedException;
    boolean isPresent(long transactionId);
    Transaction get(long transactionId);
}
