import data.Storage;
import data.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ServerTest {

    @Mock
    Storage storage;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTransaction_dataStorageIsCalled() throws Exception {
        Server server = new Server(storage);
        Transaction data = new Transaction();


        verify(storage).save(eq(data));
    }
}
