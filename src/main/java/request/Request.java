package request;

public interface Request {
    //todo also implement with standard java library

    enum HttpMethod {
        GET,
        PUT
    }
    enum Method {
        TRANSACTION,
        TYPES,
        SUM,
        UNKNOWN
    }

    boolean isValid();

    Method getMethod();
    String getParameter();

    void addPayload(String payload) throws ImpossibleToAddPayloadException;
    String getPayload();
}
