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
    public void extractMethod() throws Exception {
        String url = "/transactionservice/method";
        Request request = new Request(url);
        assertEquals("method", request.getMethod());
    }

    @Test
    public void extractParameter() throws Exception {
        String url = "/transactionservice/transaction/1024";
        Request request = new Request(url);
        assertEquals("1024", request.getParameter());
    }


    @Test
    public void ensureWholeUrlIsValid() throws Exception {
        // Valid url
        assertUrlValid("/transactionservice/transaction/123423");
        assertUrlValid("/transactionservice/sum/41");
        assertUrlValid("/transactionservice/types/test");

        // Wrong parameter type
        assertUrlNotValid("/transactionservice/sum/notinteger");
        assertUrlNotValid("/transactionservice/types/152365");
        assertUrlNotValid("/transactionservice/transaction/notinteger");

        // Wrong method DONE
        assertUrlNotValid("/transactionservice/wrongmethod/34");

        // Wrong service DONE
        assertUrlNotValid("/wrongservice/transaction/645");

        // Incomplete url DONE
        assertUrlNotValid("/transactionservice/transaction/");
        assertUrlNotValid("/transactionservice/transaction");
        assertUrlNotValid("/transactionservice/");
        assertUrlNotValid("/transactionservice");
        assertUrlNotValid("/");
        assertUrlNotValid("");

        // Not url DONE
        assertUrlNotValid(null);
    }

    private void assertUrlValid(String url) {
        Request request = new Request(url);
        assertTrue(request.isValid());
    }

    private void assertUrlNotValid(String url) {
        Request request = new Request(url);
        assertFalse(request.isValid());
    }
}
