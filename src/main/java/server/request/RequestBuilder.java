package server.request;

import httprequest.HttpRequest;

public interface RequestBuilder {

    Request buildFromHttpRequest(HttpRequest request) throws InvalidHttpRequest;
}
