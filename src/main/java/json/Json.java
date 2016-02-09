package json;

import data.TransactionTemp;

// todo implement with Gson also
public interface Json {


    String makeStatusOk();

    TransactionTemp parseJsonToTransaction(String jsonTransaction) throws JsonParseException;


}
