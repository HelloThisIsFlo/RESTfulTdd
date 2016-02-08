package json;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class JsonMockImplTest {

    private Json json;

    @Before
    public void setUp() throws Exception {
        json = new JsonMockImpl();
    }

    @Test
    public void createStatusOkRequest() throws Exception {
        String result = json.makeStatusOk();
        assertEquals(result, "{ \"status\": \"ok\" }");
    }
}