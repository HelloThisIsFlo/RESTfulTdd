package server;

import data.Storage;
import data.Transaction;
import data.inmemory.InMemoryStorageImpl;
import data.inmemory.TransactionWithId;
import httprequest.HttpRequest;
import httprequest.HttpRequestImpl;
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

import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ServerGetSumTest {

    private static final long PARENT_ID = 2371;
    Storage storage;
    RequestBuilder requestBuilder;
    Json json;
    @Mock
    RequestExecutedCallback requestExecutedCallback;
    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;
    List<TransactionWithId> transactionWithIds;
    private Server server;

    private static HttpRequest makeSumHttpRequestFromParentId(long parentId) {
        return new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/sum/" + parentId);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        json = new JsonMockImpl();
        requestBuilder = new RequestBuilderImpl(json);
        storage = new InMemoryStorageImpl();
        server = new Server(json, storage, requestBuilder);

        transactionWithIds = new ArrayList<>(5);
        transactionWithIds.add(new TransactionWithId(65665L, new Transaction(534.3542, "car", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(4445321L, new Transaction(3121.1, "boat", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(9789L, new Transaction(54.23, "truck", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(67L, new Transaction(986.4, "boat", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(32164489L, new Transaction(6554.5, "boat", PARENT_ID)));
    }

    @Test
    public void noTransactionsWithParentId_returnZero() throws Exception {
        HttpRequest httpRequest = makeSumHttpRequestFromParentId(PARENT_ID);
        server.execute(httpRequest, requestExecutedCallback);

        verify(requestExecutedCallback).onRequestExecuted(eq(json.makeFromSum(0)));
    }

    @Test
    public void singleTransactionWithParentId_returnTransactionAmount() throws Exception {
        TransactionWithId transactionWithId = transactionWithIds.get(0);
        Transaction transaction = transactionWithId.transaction;
        long transactionId = transactionWithId.transactionId;
        storage.save(transaction, transactionId);
        HttpRequest httpRequest = makeSumHttpRequestFromParentId(PARENT_ID);

        server.execute(httpRequest, requestExecutedCallback);

        verify(requestExecutedCallback).onRequestExecuted(eq(json.makeFromSum(transaction.amount)));
    }

    @Test
    public void multipleTransactionsWithParentId_returnTransactionAmount() throws Exception {
        for (TransactionWithId transactionWithId: transactionWithIds) {
            storage.save(transactionWithId.transaction, transactionWithId.transactionId);
        }

        HttpRequest httpRequest = makeSumHttpRequestFromParentId(PARENT_ID);

        server.execute(httpRequest, requestExecutedCallback);

        verify(requestExecutedCallback).onRequestExecuted(eq(json.makeFromSum(11250.5842)));
    }
}
