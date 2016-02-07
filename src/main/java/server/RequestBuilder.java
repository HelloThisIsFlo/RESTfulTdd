package server;

import httprequest.HttpRequest;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import server.request.*;

/**
 * Build a Request executable from the server
 *
 * This class allow to conceal the "switch" statement in a single class. It will also be the only class to recompile
 * when a new Request type is added
 *
 * todo verify above assertion
 */
public class RequestBuilder {

    private final HttpRequest httpRequest;

    private RequestBuilder(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public static Request fromHttpRequest(HttpRequest httpRequest) throws InvalidHttpRequest{
        return new RequestBuilder(httpRequest).build();
    }

    private Request build() throws InvalidHttpRequest {
        ensureHttpRequestValid();
        switch (httpRequest.getHttpMethod()) {
            case GET:
                return buildGetRequest();

            case PUT:
                String payload = getPayload();
                return new PutTransactionRequest(payload);

            default:
                throw new InvalidHttpRequest();
        }
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
        return new GetTransactionRequest(parameter);
    }

    private GetTypesRequest buildGetTypesRequest() {
        String typeName = httpRequest.getParameter();
        return new GetTypesRequest(typeName);
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
