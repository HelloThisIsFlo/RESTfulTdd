package data;


public interface Storage {

    void save(Transaction data, long transactionId) throws TransactionNotSavedException;
    void isPresent(Transaction data);
    Transaction get(long transactionId);
}
