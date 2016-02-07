package request;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpRequestImplTest {

    @Test
    public void extractService() throws Exception {
        String url = "/transactionservice/somethingelse";
        HttpRequestImpl request = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, url);
        String result = request.getService();
        assertEquals("transactionservice", result);
    }

    @Test
    public void extractMethod() throws Exception {
        String url = "/transactionservice/types";
        HttpRequestImpl request = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, url);
        assertEquals(HttpRequestImpl.Method.TYPES, request.getMethod());
    }

    @Test
    public void extractParameter() throws Exception {
        String url = "/transactionservice/transaction/1024";
        HttpRequestImpl request = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, url);
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
        HttpRequestImpl getRequest = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, url);
        HttpRequestImpl putRequest = new HttpRequestImpl(HttpRequestImpl.HttpMethod.PUT, url);
        assertTrue(getRequest.isValid() || putRequest.isValid());
    }

    private void assertUrlNotValid(String url) {
        HttpRequestImpl getRequest = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, url);
        HttpRequestImpl putRequest = new HttpRequestImpl(HttpRequestImpl.HttpMethod.PUT, url);
        assertFalse(getRequest.isValid() || putRequest.isValid());
    }

    private void assertRequestValid(HttpRequestImpl.HttpMethod httpMethod, String url) {
        HttpRequestImpl request = new HttpRequestImpl(httpMethod, url);
        assertTrue(request.isValid());
    }

    private void assertRequestNotValid(HttpRequestImpl.HttpMethod httpMethod, String url) {
        HttpRequestImpl request = new HttpRequestImpl(httpMethod, url);
        assertFalse(request.isValid());
    }

    @Test
    public void ensureValidRequestMethod() throws Exception {
        assertRequestValid(HttpRequestImpl.HttpMethod.GET, "/transactionservice/transaction/123423");
        assertRequestValid(HttpRequestImpl.HttpMethod.PUT, "/transactionservice/transaction/123423");
        assertRequestValid(HttpRequestImpl.HttpMethod.GET, "/transactionservice/sum/123423");
        assertRequestValid(HttpRequestImpl.HttpMethod.GET, "/transactionservice/types/test");

        assertRequestNotValid(HttpRequestImpl.HttpMethod.PUT, "/transactionservice/sum/123423");
        assertRequestNotValid(HttpRequestImpl.HttpMethod.PUT, "/transactionservice/types/test");
    }

    @Test
    public void addPayloadOnPutRequest_success() throws Exception {
        HttpRequestImpl request = new HttpRequestImpl(HttpRequestImpl.HttpMethod.PUT, "/transactionservice/transaction/123423");
        assertTrue(request.isValid());
        String payload = "PAYLOAD";
        request.addPayload(payload);
        assertEquals(request.getPayload(), payload);
    }

    @Test(expected= ImpossibleToAddPayloadException.class)
    public void addPayloadOnGetRequest_failure() throws Exception {
        HttpRequestImpl request = new HttpRequestImpl(HttpRequestImpl.HttpMethod.GET, "/transactionservice/transaction/123423");
        assertTrue(request.isValid());
        String payload = "PAYLOAD";
        request.addPayload(payload);
    }
}
