package server.request;

import server.RequestExecutedCallback;
import server.Server;

class GetSumRequest implements Request {

    private final long transactionId;

    public GetSumRequest(long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void execute(Server server, RequestExecutedCallback callback) {

    }
}
