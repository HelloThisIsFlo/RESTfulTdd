package server.request;

import server.RequestExecutedCallback;
import server.Server;

/**
 * Represent a Request to be processed by the server
 */
public interface Request {

    void execute(Server server, RequestExecutedCallback callback);

}
