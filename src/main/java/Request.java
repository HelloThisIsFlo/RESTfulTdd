import java.util.ArrayList;
import java.util.List;

public class Request {

    private static List<String> validMethods = new ArrayList<>(3);
    static {
        validMethods.add("transaction");
        validMethods.add("types");
        validMethods.add("sum");
    }

    private String url;
    private String service;
    private String method;
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

    public String getMethod() {
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
        if (hasMethod()) method = fragments[2];
        if (hasParameter()) parameter = fragments[3];
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
        return hasMethod() && validMethods.contains(method);
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
        return method.equals("transaction")
                || method.equals("sum");
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
