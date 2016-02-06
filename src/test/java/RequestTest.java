import org.junit.Test;
import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void extractService() throws Exception {
        String url = "/transactionservice/somethingelse";
        Request request = new Request(url);
        String result = request.getService();
        assertEquals("transactionservice", result);
    }

    @Test
    public void ensureRightServiceIsUsed() throws Exception {
        String correctUrl = "/transactionservice/somethingelse";
        Request request = new Request(correctUrl);
        assertTrue(request.isServiceValid());

        String wrongUrl = "/otherservice/test";
        Request wrongRequest = new Request(wrongUrl);
        assertFalse(wrongRequest.isServiceValid());
    }

    @Test
    public void extractMethod() throws Exception {
        String url = "/transactionservice/method";
        Request request = new Request(url);
        assertEquals("method", request.getMethod());
    }

    @Test
    public void ensureRightMethodIsUsed() throws Exception {
        String correctUrl = "/transactionservice/transaction";
        Request request = new Request(correctUrl);
        assertTrue(request.isMethodValid());

        String wrongUrl = "/transactionservice/notsupported";
        Request wrongRequest = new Request(wrongUrl);
        assertFalse(wrongRequest.isMethodValid());

    }
}
