package server.request;

import json.Json;
import json.mock.JsonMockImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.RequestExecutedCallback;
import server.Server;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class PutTransactionRequestTest {
    @Mock
    Server server;
    @Mock
    RequestExecutedCallback callback;

    PutTransactionRequest request;
    Json json;

    private final static long TRANSACTION_ID= 15561;
    private static final String TRANSACTION_JSON = "{ \"amount\":641.45,\"type\":\"house_insurance\",\"parent_id\":6854316581 }";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        json = new JsonMockImpl();
        request = new PutTransactionRequest(json, TRANSACTION_ID, TRANSACTION_JSON);
    }

    @Test
    public void executeSuccess_callbackCalledWithOk() throws Exception {
        request.execute(server, callback);
        verify(callback).onRequestExecuted(eq(json.makeStatusOk()));
    }
}