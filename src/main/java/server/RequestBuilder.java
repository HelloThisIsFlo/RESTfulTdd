package server;

import httprequest.HttpRequest;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import server.request.GetTransactionRequest;
import server.request.PutTransactionRequest;
import server.request.Request;
import server.request.InvalidHttpRequest;

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

    public RequestBuilder(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public Request build() throws InvalidHttpRequest {
        ensureHttpRequestValid();
        switch (httpRequest.getHttpMethod()) {
            case GET:
                long parameter = getLongParameter();
                return new GetTransactionRequest(parameter);

            case PUT:
                String payload = getPayload();
                return new PutTransactionRequest(payload);

            default:
                throw new InvalidHttpRequest();
        }
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
