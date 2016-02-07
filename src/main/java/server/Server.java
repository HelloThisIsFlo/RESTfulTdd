package server;

import data.Storage;
import data.Transaction;
import httprequest.HttpRequest;
import server.request.InvalidHttpRequest;
import server.request.Request;

public class Server {

    private Storage storage;

    public Server(Storage storage) {
        this.storage = storage;
    }

    public void execute(HttpRequest httpRequest) throws InvalidHttpRequest {





        if (httpRequest.isValid()) {
            if (httpRequest.getHttpMethod() == HttpRequest.HttpMethod.GET) {
                long transactionId = Long.parseLong(httpRequest.getParameter());
                storage.get(transactionId);
            } else {
                String parameter = httpRequest.getParameter();
                String payload = httpRequest.getPayload();
                Transaction data = new Transaction(Integer.parseInt(parameter), payload);
                storage.save(data);
            }
        }
    }

//    private Request makeRequestFromHttpRequest(HttpRequest httpRequest) throws InvalidHttpRequest {
//
//    }

    protected void save(Transaction data) {
        storage.save(data);
    }

    protected Transaction get(long transactionId) {
        return storage.get(transactionId);
    }



}
