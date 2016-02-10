package json;

import data.Transaction;

import java.util.List;

// todo implement with Gson also
public interface Json {


    String makeStatusOk();
    String makeErrorStatus();

    Transaction parseJsonToTransaction(String jsonTransaction) throws JsonParseException;
    String serializeFromTransaction(Transaction transaction);
    String makeFromTransactionIdList(List<Long> longs);

}
