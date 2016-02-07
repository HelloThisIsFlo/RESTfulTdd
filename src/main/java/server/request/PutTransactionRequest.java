package server.request;

import server.Server;

public class PutTransactionRequest implements Request {

    private final String payload;

    public PutTransactionRequest(String payload) {
        this.payload = payload;
    }

    @Override
    public void execute(Server server) {

    }
}
