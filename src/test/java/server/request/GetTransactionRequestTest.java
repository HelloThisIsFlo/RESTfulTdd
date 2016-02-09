package server.request;

import data.Transaction;
import json.Json;
import json.mock.JsonMockImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.RequestExecutedCallback;
import server.Server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GetTransactionRequestTest {

    private static final long TRANSACTION_ID = 6546854353L;
    private static final Transaction TRANSACTION = new Transaction(654.1, "computer", 6584L);
    private static final String TRANSACTION_JSON = "{ \"amount\":654.1,\"type\":\"computer\",\"parent_id\":6584 }";


    GetTransactionRequest getTransactionRequest;
    @Mock
    Server server;
    @Mock
    RequestExecutedCallback callback;
    Json json;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        json = new JsonMockImpl();
        getTransactionRequest = new GetTransactionRequest(json, TRANSACTION_ID);
    }

    @Test
    public void getTransaction_callbackCalledWithSerializedTransaction() throws Exception {
        when(server.get(eq(TRANSACTION_ID))).thenReturn(TRANSACTION);

        getTransactionRequest.execute(server, callback);
        verify(server).get(eq(TRANSACTION_ID));

        verify(callback).onRequestExecuted(eq(TRANSACTION_JSON));
    }
}