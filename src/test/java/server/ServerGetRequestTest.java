package server;

import data.Storage;
import data.Transaction;
import data.inmemory.InMemoryStorageImpl;
import data.inmemory.TransactionWithId;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ServerGetRequestTest {

    private static final long TRANSACTION_ID = 2371;
    private static final String TRANSACTION_JSON = "{ \"amount\":641.45,\"type\":\"house_insurance\",\"parent_id\":6854316581 }";
    private static final Transaction TRANSACTION = new Transaction(641.45, "house_insurance", 6854316581L);
    private static final String TEST_TYPE = "boat";
    Storage storage;
    RequestBuilder requestBuilder;
    Json json;
    @Mock
    RequestExecutedCallback requestExecutedCallback;
    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;
    List<TransactionWithId> transactionWithIds;
    private Server server;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        json = new JsonMockImpl();
        requestBuilder = new RequestBuilderImpl(json);
        storage = new InMemoryStorageImpl();
        server = new Server(json, storage, requestBuilder);

        storage.save(TRANSACTION, TRANSACTION_ID);

        transactionWithIds = new ArrayList<>(5);
        transactionWithIds.add(new TransactionWithId(656565L, new Transaction(534.3542, "car", 454684)));
        transactionWithIds.add(new TransactionWithId(4445L, new Transaction(3121.1, TEST_TYPE, 366541)));
        transactionWithIds.add(new TransactionWithId(99789L, new Transaction(54.23, "truck", 12)));
        transactionWithIds.add(new TransactionWithId(6677L, new Transaction(986.4, TEST_TYPE, 3784534534534L)));
        transactionWithIds.add(new TransactionWithId(321644889L, new Transaction(6554.5, TEST_TYPE, 53453)));
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

    @Test
    public void getTypes_callbackCalledWithCorrectListOfTypes() throws Exception {
        for (TransactionWithId transactionWithId : transactionWithIds) {
            storage.save(transactionWithId.transaction, transactionWithId.transactionId);
        }
        List<Long> expectedIds = new ArrayList<>(3); //todo maybe implement a better solution where the order doesn't matter
        expectedIds.add(321644889L);
        expectedIds.add(6677L);
        expectedIds.add(4445L);
        String expectedResult = json.makeFromTransactionIdList(expectedIds);

        HttpRequest httpRequest = makeGetRequestFromUrl("/transactionservice/types/" + TEST_TYPE);
        server.execute(httpRequest, requestExecutedCallback);
        verify(requestExecutedCallback).onRequestExecuted(eq(expectedResult));
    }

    @Test
    public void transactionNotFound_returnErrorStatusWithReason() throws Exception {
        HttpRequest httpRequest = makeGetRequestFromUrl("/transactionservice/transaction/" + 56414516L);
        server.execute(httpRequest, requestExecutedCallback);
        verify(requestExecutedCallback).onRequestExecuted(eq(json.makeStatusError("Transaction not found")));
    }
}
