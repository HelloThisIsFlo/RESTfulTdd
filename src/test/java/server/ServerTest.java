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

    private static final long TRANSACTION_ID = 2371;
    private static final String TRANSACTION_JSON = "{ \"amount\":641.45,\"type\":\"house_insurance\",\"parent_id\":6854316581 }";
    private static final Transaction TRANSACTION = new Transaction(641.45, "house_insurance", 6854316581L);
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
        httpRequest.setPayload(TRANSACTION_JSON);
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
        Transaction resultTransaction = transactionCaptor.getValue();
        assertEquals(TRANSACTION, resultTransaction);
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
        server.save(TRANSACTION, TRANSACTION_ID);
    }
}
