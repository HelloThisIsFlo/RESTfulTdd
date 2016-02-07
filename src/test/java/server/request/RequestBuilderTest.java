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
        Request request = new RequestBuilder(getTransaction).build();

        assertThat(request, instanceOf(GetTransactionRequest.class));
    }

    @Test
    public void buildPutTransactionRequest_correctRequest() throws Exception {
        HttpRequest putTransaction = new HttpRequestImpl(HttpRequest.HttpMethod.PUT, "/transactionservice/transaction/123423");
        putTransaction.setPayload("This is payload");
        Request request = new RequestBuilder(putTransaction).build();

        assertThat(request, instanceOf(PutTransactionRequest.class));
    }


}
