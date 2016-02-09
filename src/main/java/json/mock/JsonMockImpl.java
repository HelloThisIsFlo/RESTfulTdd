package json.mock;

import data.TransactionTemp;
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
    public TransactionTemp parseJsonToTransaction(String jsonTransaction) throws JsonParseException {
        JsonMockTransactionParser parser = new JsonMockTransactionParser(jsonTransaction);
        return parser.parseTransaction();
    }

    @Override
    public String serializeFromTransaction(TransactionTemp transaction) {
        JsonMockTransactionSerializer serializer = new JsonMockTransactionSerializer(transaction);
        return serializer.serializeFromTransaction();
    }
}
