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
        JsonMockParser parser = new JsonMockParser(jsonTransaction);
        return parser.parseTransaction();
    }
}
