package server;

import data.Storage;
import data.Transaction;
import data.inmemory.InMemoryStorageImpl;
import httprequest.HttpRequest;
import httprequest.HttpRequestImpl;
import httprequest.ImpossibleToAddPayloadException;
import json.Json;
import json.mock.JsonMockImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.request.RequestBuilder;
import server.request.RequestBuilderImpl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Server test with all put request implementations
 */
public class ServerPutRequestTest {

    private static final long TRANSACTION_ID = 2371;
    private static final String TRANSACTION_JSON = "{ \"amount\":641.45,\"type\":\"house_insurance\",\"parent_id\":6854316581 }";
    private static final Transaction TRANSACTION = new Transaction(641.45, "house_insurance", 6854316581L);
    Storage storage;
    RequestBuilder requestBuilder;
    Json json;
    @Mock
    RequestExecutedCallback requestExecutedCallback;

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
        storage = new InMemoryStorageImpl();
        server = new Server(json, storage, requestBuilder);
    }

    @Test
    public void putRequest_saveToStorage_returnOkStatus() throws Exception {
        HttpRequest httpRequest = makePutRequestFromUrl("/transactionservice/transaction/" + TRANSACTION_ID);
        server.execute(httpRequest, requestExecutedCallback);

        assertTrue(storage.isPresent(TRANSACTION_ID));
        assertEquals(TRANSACTION, storage.get(TRANSACTION_ID));

        verify(requestExecutedCallback).onRequestExecuted(eq(json.makeStatusOk()));
    }

    @Test
    public void failedToSaveTransaction_returnErrorStatus() throws Exception {
        HttpRequest httpRequest = makePutRequestFromUrl("/transactionservice/transaction/" + 0);
        server.execute(httpRequest, requestExecutedCallback);
        verify(requestExecutedCallback).onRequestExecuted(eq(json.makeStatusError("Transaction could not be saved")));
    }

}
