package server.request;

import data.Storage;
import server.RequestExecutedCallback;
import server.Server;

class GetSumRequest implements Request {

    private final long transactionId;

    public GetSumRequest(long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void execute(Storage storage, RequestExecutedCallback callback) {

    }
}
