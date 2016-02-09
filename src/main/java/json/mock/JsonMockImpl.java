package json.mock;

import data.Transaction;
import json.Json;
import json.JsonParseException;

/**
 * Implementation of the Json Interface that directly creates the expected strings
 */
public class JsonMockImpl implements Json {

    @Override
    public String makeStatusOk() {
        return "{ \"status\": \"ok\" }";
    }

    @Override
    public Transaction parseJsonToTransaction(String jsonTransaction) throws JsonParseException {
        JsonMockTransactionParser parser = new JsonMockTransactionParser(jsonTransaction);
        return parser.parseTransaction();
    }

    @Override
    public String serializeFromTransaction(Transaction transaction) {
        JsonMockTransactionSerializer serializer = new JsonMockTransactionSerializer(transaction);
        return serializer.serializeFromTransaction();
    }
}
