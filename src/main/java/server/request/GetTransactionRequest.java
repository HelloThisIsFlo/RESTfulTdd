package server.request;

import server.Server;

public class GetTransactionRequest implements Request {

    private final long transactionId;

    public GetTransactionRequest(long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public void execute(Server server) {

    }
}
