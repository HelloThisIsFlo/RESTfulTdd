package server.request;

import data.Storage;
import data.Transaction;
import data.TransactionNotFoundException;
import json.Json;
import server.RequestExecutedCallback;
import server.ServerException;

class GetTransactionRequest implements Request {

    private final Json json;
    private final long id;

    private Storage storage;
    private Transaction transaction;

    public GetTransactionRequest(Json json, long transactionId) {
        this.json = json;
        this.id = transactionId;
    }

    @Override
    public void execute(Storage storage, RequestExecutedCallback callback) throws ServerException {
        this.storage = storage;
        getTransaction();
        String response = json.serializeFromTransaction(transaction);
        callback.onRequestExecuted(response);
    }

    private void getTransaction() throws ServerException {
        try {
            transaction = storage.get(id);
        } catch (TransactionNotFoundException e) {
            throw new ServerException("Transaction not found");
        }
    }
}
