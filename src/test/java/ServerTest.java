import data.Storage;
import data.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import request.ImpossibleToAddPayloadException;
import request.Request;
import request.RequestImpl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ServerTest {

    private Server server;

    @Mock
    Storage storage;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        server = new Server(storage);
    }

    @Test
    public void putRequest_saveToStorage() throws Exception {
        // Make post request
        Request request = new RequestImpl(RequestImpl.HttpMethod.PUT, "/transactionservice/transaction/1024");
        String payload = "Hello, this is Payload!";
        request.addPayload(payload);

        server.execute(request);

        verify(storage).save(transactionCaptor.capture());
        String resultPayload = transactionCaptor.getValue().getPayload();
        int resultId = transactionCaptor.getValue().getTransactionId();
        assertEquals(payload, resultPayload);
        assertEquals(1024, resultId);
    }

    @Test
    public void wrongRequest_doNotSave() throws Exception {
        // Make wrong request
        Request request = new RequestImpl(RequestImpl.HttpMethod.PUT, "/sf/transaction/1024");
        String payload = "Hello, this is Payload!";
        request.addPayload(payload);

        server.execute(request);

        verify(storage, never()).save(any());
    }

    @Test
    public void requestWithNoId_doNotCrashDo_doNotSave() throws Exception {
        // Make wrong request
        Request request = new RequestImpl(RequestImpl.HttpMethod.PUT, "/transactionservice/transaction/noid");
        String payload = "Hello, this is Payload!";
        request.addPayload(payload);

        server.execute(request);

        verify(storage, never()).save(any());
    }
}
