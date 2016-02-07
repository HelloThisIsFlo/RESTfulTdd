package server.request;

import httprequest.HttpRequest;
import httprequest.HttpRequestImpl;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class RequestBuilderImplTest {

    @Test
    public void buildGetTransactionRequest_correctRequest() throws Exception {
        HttpRequest getTransaction = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/transaction/123423");
        Request request = new RequestBuilderImpl().buildFromHttpRequest(getTransaction);

        assertThat(request, instanceOf(GetTransactionRequest.class));
    }

    @Test
    public void buildPutTransactionRequest_correctRequest() throws Exception {
        HttpRequest putTransaction = new HttpRequestImpl(HttpRequest.HttpMethod.PUT, "/transactionservice/transaction/123423");
        putTransaction.setPayload("This is payload");
        Request request = new RequestBuilderImpl().buildFromHttpRequest(putTransaction);

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }

    @Test (expected = InvalidHttpRequest.class)
    public void putHttpRequestWithoutPayload_throw_exception() throws Exception {
        HttpRequest withoutPayload = new HttpRequestImpl(HttpRequest.HttpMethod.PUT, "/transactionservice/transaction/123423");
        Request request = new RequestBuilderImpl().buildFromHttpRequest(withoutPayload);

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }

    @Test (expected = InvalidHttpRequest.class)
    public void invalidHttpRequest_throw_exception() throws Exception {
        HttpRequest invalidRequest = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/invalid/123423");
        Request request = new RequestBuilderImpl().buildFromHttpRequest(invalidRequest);

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }

    @Test
    public void buildGetType_correctRequest() throws Exception {
        HttpRequest getTypes = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/types/asdf");
        Request request = new RequestBuilderImpl().buildFromHttpRequest(getTypes);

        assertThat(request, instanceOf(GetTypesRequest.class));
    }

    @Test
    public void buildGetSumRequest_correctRequest() throws Exception {
        HttpRequest getSum = new HttpRequestImpl(HttpRequest.HttpMethod.GET, "/transactionservice/sum/1365");
        Request request = new RequestBuilderImpl().buildFromHttpRequest(getSum);

        assertThat(request, instanceOf(GetSumRequest.class));
    }
}
