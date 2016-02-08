package server.request;

import data.Transaction;
import json.Json;
import server.RequestExecutedCallback;
import server.Server;

public class PutTransactionRequest implements Request {

    private final Json json;
    private final String payload;
    private final long id;

    public PutTransactionRequest(Json json, long transactionId, String payload) {
        this.json = json;
        this.payload = payload;
        this.id = transactionId;
    }

    @Override
    public void execute(Server server, RequestExecutedCallback callback) {
        Transaction transaction = new Transaction(id, payload);
        server.save(transaction);
        callback.onRequestExecuted("{ \"status\": \"ok\" }");
    }
}
