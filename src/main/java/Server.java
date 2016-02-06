import data.Storage;
import data.Transaction;

public class Server {

    private Storage storage;

    public Server(Storage storage) {
        this.storage = storage;
    }

    public void execute(Request request) {

        String parameter = request.getParameter();
        String payload = request.getPayload();
        Transaction data = new Transaction(Integer.parseInt(parameter), payload);
        storage.save(data);
    }
}
