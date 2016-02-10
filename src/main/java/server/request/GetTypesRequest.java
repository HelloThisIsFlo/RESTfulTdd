package server.request;

import data.Storage;
import json.Json;
import server.RequestExecutedCallback;
import server.Server;

import java.util.ArrayList;
import java.util.List;

class GetTypesRequest implements Request {

    private final Json json;
    private final String type;

    public GetTypesRequest(Json json, String type) {
        this.json = json;
        this.type = type;
    }

    @Override
    public void execute(Storage storage, RequestExecutedCallback callback) {
        List<Long> ids = storage.getFromType(type);
        String jsonIds = json.makeFromTransactionIdList(ids);

        callback.onRequestExecuted(jsonIds);
    }
}
