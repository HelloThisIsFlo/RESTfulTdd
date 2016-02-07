package data;

public class Transaction {

    private final long transactionId;
    private final String payload;

    public Transaction(long transactionId, String payload) {
        this.transactionId = transactionId;
        this.payload = payload;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getPayload() {
        return payload;
    }
}
