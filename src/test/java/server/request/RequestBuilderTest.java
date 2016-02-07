package server.request;

import httprequest.HttpRequest;
import httprequest.HttpRequestImpl;
import org.junit.Test;
import server.RequestBuilder;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class RequestBuilderTest {


    @Test
    public void buildGetTransactionRequest_correctRequest() throws Exception {
        HttpRequest getTransaction = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/transaction/123423");
        Request request = RequestBuilder.fromHttpRequest(getTransaction);

        assertThat(request, instanceOf(GetTransactionRequest.class));
    }

    @Test
    public void buildPutTransactionRequest_correctRequest() throws Exception {
        HttpRequest putTransaction = new HttpRequestImpl(HttpRequest.HttpMethod.PUT, "/transactionservice/transaction/123423");
        putTransaction.setPayload("This is payload");
        Request request = RequestBuilder.fromHttpRequest(putTransaction);

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }

    @Test (expected = InvalidHttpRequest.class)
    public void putHttpRequestWithoutPayload_throw_exception() throws Exception {
        HttpRequest withoutPayload = new HttpRequestImpl(HttpRequest.HttpMethod.PUT, "/transactionservice/transaction/123423");
        Request request = RequestBuilder.fromHttpRequest(withoutPayload);

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }

    @Test (expected = InvalidHttpRequest.class)
    public void invalidHttpRequest_throw_exception() throws Exception {
        HttpRequest invalidRequest = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/invalid/123423");
        Request request = RequestBuilder.fromHttpRequest(invalidRequest);

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }

    @Test
    public void buildGetType_correctRequest() throws Exception {
        HttpRequest getTypes = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/types/asdf");
        Request request = RequestBuilder.fromHttpRequest(getTypes);

        assertThat(request, instanceOf(GetTypesRequest.class));

    }

    @Test
    public void buildGetSumRequest_correctRequest() throws Exception {
        HttpRequest getSum = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/sum/1365");
        Request request = RequestBuilder.fromHttpRequest(getSum);

        assertThat(request, instanceOf(GetSumRequest.class));
    }
}
