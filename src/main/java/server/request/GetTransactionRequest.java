package server.request;

import data.Transaction;
import json.Json;
import server.RequestExecutedCallback;
import server.Server;
import server.ServerException;

public class GetTransactionRequest implements Request {

    private final Json json;
    private final long id;

    public GetTransactionRequest(Json json, long transactionId) {
        this.json = json;
        this.id = transactionId;
    }

    @Override
    public void execute(Server server, RequestExecutedCallback callback) throws ServerException {
        Transaction transaction = server.get(id);
        String response;
        if (transaction != null) { // todo improve error handling here
            response = json.serializeFromTransaction(transaction);
        } else {
            response = json.makeErrorStatus();
        }
        callback.onRequestExecuted(response);
    }
}
