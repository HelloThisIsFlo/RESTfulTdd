package server.request;

import data.Storage;
import server.RequestExecutedCallback;
import server.Server;
import server.ServerException;

/**
 * Represent a Request to be processed by the server
 */
public interface Request {

    void execute(Storage storage, RequestExecutedCallback callback) throws ServerException;

}
