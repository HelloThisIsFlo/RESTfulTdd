package data;


public interface Storage {

    void save(Transaction data);
    void isPresent(Transaction data);
    Transaction get(long transactionId);
}
