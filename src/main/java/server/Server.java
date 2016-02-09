package server;

import data.Storage;
import data.Transaction;
import data.TransactionNotSavedException;
import httprequest.HttpRequest;
import server.request.InvalidHttpRequest;
import server.request.Request;
import server.request.RequestBuilder;

public class Server {

    private final Storage storage;
    private final RequestBuilder requestBuilder;

    public Server(Storage storage, RequestBuilder requestBuilder) {
        this.storage = storage;
        this.requestBuilder = requestBuilder;
    }

    public void execute(HttpRequest httpRequest, RequestExecutedCallback callback) throws InvalidHttpRequest {
        Request request = requestBuilder.buildFromHttpRequest(httpRequest);

        try {
            request.execute(this, callback);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    public void save(Transaction data) throws ServerException{
        try {
            storage.save(data);
        } catch (TransactionNotSavedException e) {
            throw new ServerException("Transaction could not be saved");
        }
    }

    public Transaction get(long transactionId) {
        return storage.get(transactionId);
    }
}
