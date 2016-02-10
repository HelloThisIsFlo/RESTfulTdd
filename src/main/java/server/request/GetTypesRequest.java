package server.request;

import server.RequestExecutedCallback;
import server.Server;

class GetTypesRequest implements Request {

    private final String type;

    public GetTypesRequest(String type) {
        this.type = type;
    }

    @Override
    public void execute(Server server, RequestExecutedCallback callback) {

    }
}
