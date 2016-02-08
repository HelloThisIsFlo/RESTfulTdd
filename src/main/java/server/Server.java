package server;

import data.Storage;
import data.Transaction;
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

        request.execute(this, callback);
    }

    public void save(Transaction data) {
        storage.save(data);
    }

    public Transaction get(long transactionId) {
        return storage.get(transactionId);
    }
}
