package request;


import java.util.HashMap;
import java.util.Map;

public class RequestImpl implements Request {

    private static String validService = "transactionservice";
    private static Map<String, Method> validMethods = new HashMap<>(3);

    static {
        validMethods.put("transaction", Method.TRANSACTION);
        validMethods.put("types", Method.TYPES);
        validMethods.put("sum", Method.SUM);
    }

    private final HttpMethod httpMethod;
    private final String url;
    private String service;
    private Method method;
    private String parameter;
    private String[] fragments;
    private String payload;

    public RequestImpl(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
        if (url != null) {
            extractInfo();
        }
    }

    public String getService() {
        return service;
    }

    public Method getMethod() {
        return method;
    }

    public String getParameter() {
        return parameter;
    }

    public boolean isValid() {
        return hasAllFragmentParts()
                && isMethodValid()
                && isServiceValid()
                && isParameterValid()
                && isHttpMethodValid();
    }

    public void addPayload(String payload) throws ImpossibleToAddPayloadException {
        if (acceptsPayload()) {
            this.payload = payload;
        } else {
            throw new ImpossibleToAddPayloadException();
        }
    }

    public String getPayload() {
        return payload;
    }

    private boolean acceptsPayload() {
        return httpMethod == HttpMethod.PUT;
    }

    private void extractInfo() {
        fragments = url.split("/");
        if (hasService()) service = fragments[1];
        if (hasMethod()) method = getMethodFromString(fragments[2]);
        if (hasParameter()) parameter = fragments[3];
    }

    private Method getMethodFromString(String methodName) {
        Method method = validMethods.get(methodName);
        if (method == null) {
            method = Method.UNKNOWN;
        }
        return method;
    }

    private boolean hasService() {
        return fragmentAtPositionIsAvailable(1);
    }

    private boolean hasMethod() {
        return fragmentAtPositionIsAvailable(2);
    }

    private boolean hasParameter() {
        return fragmentAtPositionIsAvailable(3);
    }

    private boolean fragmentAtPositionIsAvailable(int position) {
        return fragments != null && fragments.length > position && !fragments[position].isEmpty();
    }

    private boolean isMethodValid() {
        return hasMethod() && method != Method.UNKNOWN;
    }

    private boolean isServiceValid() {
        return hasService() && validService.equals(service);
    }

    private boolean isParameterValid() {
        if (hasParameter()) {
            if (isParameterExpectedToBeLong()) { //todo maybe remove. Not sure if Request's responsability
                return isParameterLong();
            } else {
                return !isParameterLong();
            }
        } else {
            return false;
        }
    }

    private boolean isHttpMethodValid() {
        if (httpMethod == HttpMethod.PUT) {
            return doesMethodAcceptPut();
        } else if (httpMethod == HttpMethod.GET){
            return doesMethodAcceptGet();
        }
        return false;
    }

    private boolean doesMethodAcceptPut() {
        return method == Method.TRANSACTION;
    }

    private boolean doesMethodAcceptGet() {
        return true; // All method accept get
    }

    private boolean hasAllFragmentParts() {
        return hasParameter() && hasService() && hasMethod();
    }

    private boolean isParameterExpectedToBeLong() {
        if (hasMethod()) {
            return method == Method.TRANSACTION
                    || method == Method.SUM;
        } else {
            return false;
        }
    }

    private boolean isParameterLong() {
        try {
            //noinspection ResultOfMethodCallIgnored
            Long.parseLong(parameter);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
