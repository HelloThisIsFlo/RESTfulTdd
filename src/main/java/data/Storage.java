package data;


public interface Storage {

    void save(Transaction data) throws TransactionNotSavedException;
    void isPresent(Transaction data);
    Transaction get(long transactionId);
}
