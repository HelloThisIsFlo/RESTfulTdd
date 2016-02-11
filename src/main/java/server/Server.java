package server;

import data.Storage;
import httprequest.HttpRequest;
import json.Json;
import server.request.InvalidHttpRequest;
import server.request.Request;
import server.request.RequestBuilder;

public class Server {

    private final Json json;
    private final Storage storage;
    private final RequestBuilder requestBuilder;

    public Server(Json json, Storage storage, RequestBuilder requestBuilder) {
        this.json = json;
        this.storage = storage;
        this.requestBuilder = requestBuilder;
    }

    public void execute(HttpRequest httpRequest, RequestExecutedCallback callback) throws InvalidHttpRequest {
        Request request = requestBuilder.buildFromHttpRequest(httpRequest);

        try {
            request.execute(storage, callback);
        } catch (ServerException e) {
            callback.onRequestExecuted(json.makeStatusError(e.getMessage()));
        }
    }

}
