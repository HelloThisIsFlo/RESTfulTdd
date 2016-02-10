package server.request;

import data.Transaction;
import json.Json;
import json.JsonParseException;
import server.RequestExecutedCallback;
import server.Server;
import server.ServerException;

class PutTransactionRequest implements Request {

    private final Json json;
    private final String payload;
    private final long id;
    private Transaction transaction;

    public PutTransactionRequest(Json json, long transactionId, String payload) {
        this.json = json;
        this.payload = payload;
        this.id = transactionId;
    }

    @Override
    public void execute(Server server, RequestExecutedCallback callback) throws ServerException {
        parseTransaction();
        server.save(transaction, id);
        callback.onRequestExecuted(json.makeStatusOk());
    }

    private void parseTransaction() throws ServerException {
        try {
            transaction = json.parseJsonToTransaction(payload);
        } catch (JsonParseException e) {
            throw new ServerException("Problem parsing Transaction", e);
        }
    }
}
