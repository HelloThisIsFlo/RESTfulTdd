package server;

import data.Storage;
import data.Transaction;
import data.TransactionNotSavedException;
import json.Json;
import json.mock.JsonMockImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import httprequest.HttpRequest;
import httprequest.HttpRequestImpl;
import httprequest.ImpossibleToAddPayloadException;
import server.request.InvalidHttpRequest;
import server.request.RequestBuilder;
import server.request.RequestBuilderImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServerTest {

    private static String PAYLOAD = "Hello this is Payload!";
    private static long TRANSACTION_ID = 2371;
    @Mock
    Storage storage;
    RequestBuilder requestBuilder;
    Json json;
    @Mock
    RequestExecutedCallback requestExecutedCallback;
    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;
    private Server server;

    private static HttpRequest makePutRequestFromUrl(String url) throws ImpossibleToAddPayloadException {
        HttpRequest httpRequest = new HttpRequestImpl(HttpRequestImpl.HttpMethod.PUT, url);
        httpRequest.setPayload(PAYLOAD);
        return httpRequest;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        json = new JsonMockImpl();
        requestBuilder = new RequestBuilderImpl(json);
        server = new Server(storage, requestBuilder);
    }

    @Test
    public void putRequest_saveToStorage() throws Exception {
        HttpRequest httpRequest = makePutRequestFromUrl("/transactionservice/transaction/" + TRANSACTION_ID);
        server.execute(httpRequest, requestExecutedCallback);

        verify(storage).save(transactionCaptor.capture(), eq(TRANSACTION_ID));
        String resultPayload = transactionCaptor.getValue().getPayload();
        long resultId = transactionCaptor.getValue().getTransactionId();
        assertEquals(PAYLOAD, resultPayload);
        assertEquals(TRANSACTION_ID, resultId);
    }

    @Test (expected = InvalidHttpRequest.class)
    public void wrongRequest_throwException() throws Exception {
        HttpRequest httpRequest = makePutRequestFromUrl("/sf/transaction/" + TRANSACTION_ID);
        server.execute(httpRequest, requestExecutedCallback);
        verify(storage, never()).save(any(), any());
    }

    @Test (expected = InvalidHttpRequest.class)
    public void requestWithNoId_throwException() throws Exception {
        HttpRequest httpRequest = makePutRequestFromUrl("/transactionservice/transaction/noid");
        server.execute(httpRequest, requestExecutedCallback);
        verify(storage, never()).save(any(), any());
    }

    @Test
    public void getRequest_retrieveTransaction() throws Exception {
        HttpRequest httpRequest = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, "/transactionservice/transaction/" + TRANSACTION_ID);

        server.execute(httpRequest, requestExecutedCallback);
        verify(storage).get(eq(TRANSACTION_ID));
    }

    @Test (expected = ServerException.class)
    public void failToSaveTransaction_throwServerException() throws Exception {
        doThrow(new TransactionNotSavedException()).when(storage).save(any(), anyLong());
        server.save(new Transaction(TRANSACTION_ID, PAYLOAD), TRANSACTION_ID);
    }
}
