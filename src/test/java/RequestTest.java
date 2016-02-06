import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void extractService() throws Exception {
        String correctUrl = "/transactionservice/somethingelse";
        Request request = new Request(correctUrl);
        String result = request.getService();
        assertEquals("transactionservice", result);

        String wrongUrl = "/otherservice/test";
        Request wrongRequest = new Request(wrongUrl);
        String wrongResult = wrongRequest.getService();

        assertNotEquals("transactionservice", wrongResult);
    }
}
