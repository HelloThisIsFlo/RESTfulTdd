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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.request.RequestBuilder;
import server.request.RequestBuilderImpl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServerGetRequestTest {

    private static final long TRANSACTION_ID = 2371;
    private static final String TRANSACTION_JSON = "{ \"amount\":641.45,\"type\":\"house_insurance\",\"parent_id\":6854316581 }";
    private static final Transaction TRANSACTION = new Transaction(641.45, "house_insurance", 6854316581L);
    Storage storage;
    RequestBuilder requestBuilder;
    Json json;
    @Mock
    RequestExecutedCallback requestExecutedCallback;
    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;
    private Server server;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        json = new JsonMockImpl();
        requestBuilder = new RequestBuilderImpl(json);
        storage = new InMemoryStorageImpl();
        server = new Server(storage, requestBuilder);

        storage.save(TRANSACTION, TRANSACTION_ID);
    }

    private static HttpRequest makeGetRequestFromUrl(String url) throws ImpossibleToAddPayloadException {
        return new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, url);
    }

    @Test
    public void getTransaction_callbackCalledWithSerializedTransaction() throws Exception {
        HttpRequest httpRequest = makeGetRequestFromUrl("/transactionservice/transaction/" + TRANSACTION_ID);
        server.execute(httpRequest, requestExecutedCallback);
        verify(requestExecutedCallback).onRequestExecuted(eq(TRANSACTION_JSON));
    }
}
