package json;

import data.Transaction;

// todo implement with Gson also
public interface Json {


    String makeStatusOk();
    String makeErrorStatus();

    Transaction parseJsonToTransaction(String jsonTransaction) throws JsonParseException;
    String serializeFromTransaction(Transaction transaction);

}
