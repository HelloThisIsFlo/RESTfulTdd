package json;

import data.Transaction;
import json.mock.JsonMockImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class JsonMockImplTest {

    private Json json;

    @Before
    public void setUp() throws Exception {
        json = new JsonMockImpl();
    }

    @Test
    public void createStatusOkRequest() throws Exception {
        String result = json.makeStatusOk();
        assertEquals(result, "{ \"status\": \"ok\" }");
    }

    @Test
    public void testParseTransactionFromJsonString() throws Exception {
        String jsonTransaction = "{ \"amount\":250.5,\"type\":\"This is a test\",\"parent_id\":25043 }";
        Transaction transaction = json.parseJsonToTransaction(jsonTransaction);

        assertEquals(250.5, transaction.amount, 0.1);
        assertEquals("This is a test", transaction.type);
        assertEquals(25043, transaction.parentId);
    }

    @Test (expected = JsonParseException.class)
    public void parseError_throwParseError() throws Exception {
        String jsonTransaction = "{ \"amount\":\"Oooops this is an error!!\",\"type\":\"This is a test\",\"parent_id\":25043 }";
        json.parseJsonToTransaction(jsonTransaction);
    }


    @Test
    public void serializeFromTransaction() throws Exception {
        Transaction transaction = new Transaction(532.12, "turtle", 25888801);
        String expectedJsonResult = "{ \"amount\":532.12,\"type\":\"turtle\",\"parent_id\":25888801 }";

        String jsonResult = json.serializeFromTransaction(transaction);

        assertEquals(expectedJsonResult, jsonResult);
    }
}