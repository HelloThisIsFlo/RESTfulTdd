package json.mock;

import java.util.List;

public class JsonMockIdListSerializer {

    private final List<Long> transactionIds;
    private String json;

    public JsonMockIdListSerializer(List<Long> transactionIds) {
        this.transactionIds = transactionIds;
    }

    public String serializeFromIds() {
        json = "";
        appendHeader();
        appendIdExceptLastOne();
        appendLastId();
        appendFooter();
        return json;
    }

    private void appendHeader() {
        json += "[";
    }

    private void appendIdExceptLastOne() {
        int size = transactionIds.size();
        for (int i = 0; i <= size - 2; i++) {
            long id = transactionIds.get(i);
            appendId(id);
            appendComma();
        }
    }

    private void appendLastId() {
        int size = transactionIds.size();
        long lastId = transactionIds.get(size - 1);
        appendId(lastId);
    }

    private void appendId(long id) {
        json += Long.toString(id);
    }

    private void appendComma() {
        json += ", ";
    }

    private void appendFooter() {
        json += "]";
    }
}
