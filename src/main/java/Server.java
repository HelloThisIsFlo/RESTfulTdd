import data.Storage;
import data.Transaction;
import request.Request;

public class Server {

    private Storage storage;

    public Server(Storage storage) {
        this.storage = storage;
    }

    public void execute(Request request) {
        if (request.isValid()) {
            if (request.getHttpMethod() == Request.HttpMethod.GET) {
                long transactionId = Long.parseLong(request.getParameter());
                storage.get(transactionId);
            } else {
                String parameter = request.getParameter();
                String payload = request.getPayload();
                Transaction data = new Transaction(Integer.parseInt(parameter), payload);
                storage.save(data);
            }
        }
    }


}
