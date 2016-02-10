package server.request;

import data.Storage;
import data.Transaction;
import data.TransactionNotSavedException;
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
    public void execute(Storage storage, RequestExecutedCallback callback) throws ServerException {
        parseTransaction();
        saveTransactionToStorage(storage);
        callback.onRequestExecuted(json.makeStatusOk());
    }

    private void saveTransactionToStorage(Storage storage) throws ServerException {
        try {
            storage.save(transaction, id);
        } catch (TransactionNotSavedException e) {
            throw new ServerException("Transaction could not be saved", e);
        }
    }

    private void parseTransaction() throws ServerException {
        try {
            transaction = json.parseJsonToTransaction(payload);
        } catch (JsonParseException e) {
            throw new ServerException("Problem parsing Transaction", e);
        }
    }
}
