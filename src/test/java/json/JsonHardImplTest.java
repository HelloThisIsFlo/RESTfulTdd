package json;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class JsonHardImplTest {

    private Json json;

    @Before
    public void setUp() throws Exception {
        json = new JsonHardImpl();
    }

    @Test
    public void createStatusOkRequest() throws Exception {
        String result = json.createStatusOk();
        assertEquals(result, "{ \"status\": \"ok\" }");
    }
}