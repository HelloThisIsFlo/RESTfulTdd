package server.request;

import data.Transaction;
import server.Server;

public class PutTransactionRequest implements Request {

    private final String payload;
    private final long id;

    public PutTransactionRequest(long transactionId, String payload) {
        this.payload = payload;
        this.id = transactionId;
    }

    @Override
    public void execute(Server server) {
        Transaction transaction = new Transaction(id, payload);
        server.save(transaction);
    }
}
