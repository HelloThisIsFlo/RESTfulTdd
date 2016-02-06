import java.util.HashMap;
import java.util.Map;

public class Request {

    public enum Method {
        TRANSACTION,
        TYPES,
        SUM,
        UNKNOWN
    }
    private static Map<String, Method> validMethods = new HashMap<>(3);
    static {
        validMethods.put("transaction", Method.TRANSACTION);
        validMethods.put("types", Method.TYPES);
        validMethods.put("sum", Method.SUM);
    }

    private String url;
    private String service;
    private Method method;
    private String parameter;
    private String[] fragments;

    public Request(String url) {
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
                && isParameterValid();
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
        return hasService() && "transactionservice".equals(service);
    }

    private boolean isParameterValid() {
        if (hasAllFragmentParts()) {
            if (isParameterExpectedToBeLong()) {
                return isParameterLong();
            } else {
                return !isParameterLong();
            }
        } else {
            return false;
        }
    }

    private boolean hasAllFragmentParts() {
        return hasParameter() && hasService() && hasMethod();
    }

    private boolean isParameterExpectedToBeLong() {
        return method == Method.TRANSACTION
                || method == Method.SUM;
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
