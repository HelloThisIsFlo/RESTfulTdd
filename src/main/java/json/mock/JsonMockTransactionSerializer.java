package json.mock;

import data.Transaction;

class JsonMockTransactionSerializer {

    private final Transaction transaction;
    private String json;

    public JsonMockTransactionSerializer(Transaction transaction) {
        this.transaction = transaction;
        this.json = "";
    }

    public String serializeFromTransaction() {
        appendHeader();
        appendAmount();
        appendType();
        appendParentId();
        appendFooter();
        return json;
    }

    private void appendHeader() {
        json += "{ ";
    }

    private void appendAmount() {
        double amount = transaction.amount;
        appendJsonValue("amount", Double.toString(amount));
        appendComma();
    }

    private void appendType() {
        String type = wrapWithDoubleQuote(transaction.type);
        appendJsonValue("type", type);
        appendComma();
    }

    private String wrapWithDoubleQuote(String toWrap) {
        if (toWrap != null) {
            return  "\"" + toWrap + "\"";
        } else {
            return "";
        }
    }

    private void appendParentId() {
        long parentId = transaction.parentId;
        appendJsonValue("parent_id", Long.toString(parentId));
    }

    private void appendJsonValue(String key, String value) {
        json += "\"" + key + "\":" + value;
    }

    private void appendComma() {
        json += ",";
    }

    private void appendFooter() {
        json += " }";
    }
}
