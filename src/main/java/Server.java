import data.Storage;
import data.Transaction;
import request.HttpRequest;

public class Server {

    private Storage storage;

    public Server(Storage storage) {
        this.storage = storage;
    }

    public void execute(HttpRequest httpRequest) {
        if (httpRequest.isValid()) {
            if (httpRequest.getHttpMethod() == HttpRequest.HttpMethod.GET) {
                long transactionId = Long.parseLong(httpRequest.getParameter());
                storage.get(transactionId);
            } else {
                String parameter = httpRequest.getParameter();
                String payload = httpRequest.getPayload();
                Transaction data = new Transaction(Integer.parseInt(parameter), payload);
                storage.save(data);
            }
        }
    }


}
