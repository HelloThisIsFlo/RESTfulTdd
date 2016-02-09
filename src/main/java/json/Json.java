package json;

import data.Transaction;

// todo implement with Gson also
public interface Json {


    String makeStatusOk();

    Transaction parseJsonToTransaction(String jsonTransaction) throws JsonParseException;
    String serializeFromTransaction(Transaction transaction);

}
