import data.Storage;
import data.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import request.ImpossibleToAddPayloadException;
import request.Request;
import request.RequestImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServerTest {

    private static String PAYLOAD = "Hello this is Payload!";
    private static long TRANSACTION_ID = 2371;
    @Mock
    Storage storage;
    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;
    private Server server;

    private static Request makePutRequestFromUrl(String url) throws ImpossibleToAddPayloadException {
        Request request = new RequestImpl(RequestImpl.HttpMethod.PUT, url);
        request.addPayload(PAYLOAD);
        return request;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        server = new Server(storage);
    }

    @Test
    public void putRequest_saveToStorage() throws Exception {
        Request request = makePutRequestFromUrl("/transactionservice/transaction/" + TRANSACTION_ID);
        server.execute(request);

        verify(storage).save(transactionCaptor.capture());
        String resultPayload = transactionCaptor.getValue().getPayload();
        long resultId = transactionCaptor.getValue().getTransactionId();
        assertEquals(PAYLOAD, resultPayload);
        assertEquals(TRANSACTION_ID, resultId);
    }

    @Test
    public void wrongRequest_doNotSave() throws Exception {
        Request request = makePutRequestFromUrl("/sf/transaction/" + TRANSACTION_ID);
        server.execute(request);
        verify(storage, never()).save(any());
    }

    @Test
    public void requestWithNoId_doNotCrashDo_doNotSave() throws Exception {
        Request request = makePutRequestFromUrl("/transactionservice/transaction/noid");
        server.execute(request);
        verify(storage, never()).save(any());
    }

    @Test
    public void getRequest_retrieveTransaction() throws Exception {
        Request request = new RequestImpl(RequestImpl.HttpMethod.GET, "/transactionservice/transaction/" + TRANSACTION_ID);

        server.execute(request);
        verify(storage).get(eq(TRANSACTION_ID));

    }
}
