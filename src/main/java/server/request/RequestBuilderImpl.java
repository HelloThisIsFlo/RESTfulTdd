package server.request;

import httprequest.HttpRequest;
import json.Json;

/**
 * Build a Request executable from the server
 *
 * This class allow to conceal the "switch" statement in a single class. It will also be the only class to recompile
 * when a new Request type is added
 *
 * I think this is true because the implementation of the RequestBuilder is abstracted by an interface so the flow of
 * dependencies points towards this interface.
 * Then when a new Request type is added, only the implementation recompile not the object using it through the
 * interface.
 */
public class RequestBuilderImpl implements RequestBuilder {

    private final Json json;
    private HttpRequest httpRequest;

    public RequestBuilderImpl(Json json) {
        this.json = json;
    }

    public Request buildFromHttpRequest(HttpRequest httpRequest) throws InvalidHttpRequest{
        this.httpRequest = httpRequest;
        ensureHttpRequestValid();
        switch (httpRequest.getHttpMethod()) {
            case GET:
                return buildGetRequest();

            case PUT:
                return buildPutRequest();

            default:
                throw new InvalidHttpRequest();
        }
    }

    private Request buildPutRequest() throws InvalidHttpRequest {
        String payload = getPayload();
        long transactionId = getLongParameter();
        return new PutTransactionRequest(json, transactionId, payload);
    }

    private Request buildGetRequest() throws InvalidHttpRequest {
        switch (httpRequest.getMethod()) {
            case TRANSACTION:
                return buildGetTransactionRequest();

            case TYPES:
                return buildGetTypesRequest();

            case SUM:
                return buildGetSumRequest();

            default:
                throw new InvalidHttpRequest();
        }
    }

    private GetTransactionRequest buildGetTransactionRequest() throws InvalidHttpRequest {
        long parameter = getLongParameter();
        return new GetTransactionRequest(json, parameter);
    }

    private GetTypesRequest buildGetTypesRequest() {
        String typeName = httpRequest.getParameter();
        return new GetTypesRequest(json, typeName);
    }

    private GetSumRequest buildGetSumRequest() throws InvalidHttpRequest {
        long parameter = getLongParameter();
        return new GetSumRequest(parameter);
    }

    private long getLongParameter() throws InvalidHttpRequest {
        String parameter = httpRequest.getParameter();
        try {
            return Long.parseLong(parameter);
        } catch (NumberFormatException e) {
            throw new InvalidHttpRequest();
        }
    }

    private String getPayload() throws InvalidHttpRequest {
        String payload = httpRequest.getPayload();
        if (payload != null) {
            return payload;
        } else {
            throw new InvalidHttpRequest();
        }
    }

    private void ensureHttpRequestValid() throws InvalidHttpRequest{
        if (!httpRequest.isValid()) {
            throw new InvalidHttpRequest();
        }
    }




}
