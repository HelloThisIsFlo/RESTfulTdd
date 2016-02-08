package json;

/**
 * Implementation of the Json Interface that directly creates the expected strings
 */
public class JsonMockImpl implements Json{

    @Override
    public String makeStatusOk() {
        return "{ \"status\": \"ok\" }";
    }
}
