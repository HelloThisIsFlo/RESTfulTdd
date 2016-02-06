import java.util.ArrayList;
import java.util.List;

public class Request {

    private String url;
    private String service;
    private String method;

    private static List<String> validMethods = new ArrayList<>(3);
    static {
        validMethods.add("transaction");
        validMethods.add("type");
        validMethods.add("sum");
    }

    public Request(String url) {
        this.url = url;
        extractServiceAndMethod();
    }

    private void extractServiceAndMethod() {
        String[] fragments = url.split("/");
        service = fragments[1];
        method = fragments[2];
    }

    public String getService() {
        return service;
    }

    public boolean isServiceValid() {
        return "transactionservice".equals(service);
    }

    public String getMethod() {
        return method;
    }

    public boolean isMethodValid() {
        return validMethods.contains(method);
    }
}
