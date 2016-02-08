package json;

/**
 * Implementation of the Json Interface that directly creates the expected strings
 */
public class JsonHardImpl implements Json{

    @Override
    public String createStatusOk() {
        return "{ \"status\": \"ok\" }";
    }
}
