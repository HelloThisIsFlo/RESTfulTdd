package json;

import data.Transaction;
import json.mock.JsonMockImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void makeStatusErrorFromMessage() throws Exception {
        String message = "There was a problem!";
        String result = json.makeStatusError(message);
        assertEquals("{ \"status\": \"error\", \"reason\": \"There was a problem!\" }", result);
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

    @Test
    public void makeJsonListFromListOfLong() throws Exception {
        List<Long> longs = new ArrayList<>(4);
        longs.add(654654L);
        longs.add(125564L);
        longs.add(651L);
        longs.add(98465132L);
        String expectedResult = "[654654, 125564, 651, 98465132]";

        String result = json.makeFromTransactionIdList(longs);

        assertEquals(expectedResult, result);
    }

    @Test
    public void makeSumResult() throws Exception {
        String result = json.makeFromSum(5.5);
        String expected = "{\"sum\":5.5}";

        assertEquals(expected, result);
    }
}