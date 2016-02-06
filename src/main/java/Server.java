import data.Storage;
import data.Transaction;
import request.Request;
import request.RequestImpl;

public class Server {

    private Storage storage;

    public Server(Storage storage) {
        this.storage = storage;
    }

    public void execute(RequestImpl request) {

        String parameter = request.getParameter();
        String payload = request.getPayload();
        Transaction data = new Transaction(Integer.parseInt(parameter), payload);
        storage.save(data);
    }

    public void isCaughtExceptionDetected() {
        try {
            throwException();
        } catch (TransactionIdNotAvailable transactionIdNotAvailable) {
            transactionIdNotAvailable.printStackTrace();
        }
    }

    private void throwException() throws TransactionIdNotAvailable {
        throw new TransactionIdNotAvailable();
    }
}
