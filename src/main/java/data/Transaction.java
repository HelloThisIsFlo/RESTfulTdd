package data;

public class Transaction {

    private final int transactionId;
    private final String payload;

    public Transaction(int transactionId, String payload) {
        this.transactionId = transactionId;
        this.payload = payload;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getPayload() {
        return payload;
    }
}
