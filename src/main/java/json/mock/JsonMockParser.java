package json.mock;

import data.TransactionTemp;
import json.JsonParseException;

class JsonMockParser {

    private final String json;
    private String[] fragments;

    private double amount;
    private String type;
    private long parentId;

    public JsonMockParser(String json) {
        this.json = json;
    }

    TransactionTemp parseTransaction() throws JsonParseException {
        fragments = json.split(",");

        extractAmount();
        extractType();
        extractParentId();
        return new TransactionTemp(amount, type, parentId);
    }

    private void extractAmount() throws JsonParseException {
        String amountFragment = fragments[0];
        String amountString = amountFragment.substring(11);
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            throw new JsonParseException("Amount couldn't be parsed to double");
        }
    }

    private void extractType() {
        String typeFragment = fragments[1];
        int l = typeFragment.length();
        type = typeFragment.substring(8, l - 1);
    }

    private void extractParentId() throws JsonParseException {
        String parentFragment = fragments[2];
        int l = parentFragment.length();
        String parentString = parentFragment.substring(12, l - 2);
        try {
            parentId = Long.parseLong(parentString);
        } catch (NumberFormatException e) {
            throw new JsonParseException("Parent ID couldn't be parsed to long");
        }
    }
}
