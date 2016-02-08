package server.request;

import server.RequestExecutedCallback;
import server.Server;

public class GetTransactionRequest implements Request {

    private final long id;

    public GetTransactionRequest(long transactionId) {
        this.id = transactionId;
    }

    @Override
    public void execute(Server server, RequestExecutedCallback callback) {
        server.get(id);
    }
}
