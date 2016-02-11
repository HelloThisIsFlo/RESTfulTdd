package server.request;

import data.Storage;
import data.Transaction;
import json.Json;
import server.RequestExecutedCallback;

import java.util.List;

class GetSumRequest implements Request {

    private final Json json;
    private final long parentId;

    public GetSumRequest(Json json, long parentId) {
        this.json = json;
        this.parentId = parentId;
    }

    @Override
    public void execute(Storage storage, RequestExecutedCallback callback) {
        List<Transaction> transactions = storage.getFromParentId(parentId);
        if (transactions.isEmpty()) {
            callback.onRequestExecuted(json.makeFromSum(0));
        } else {
            double sum = makeSumOfAmounts(transactions);
            callback.onRequestExecuted(json.makeFromSum(sum));
        }
    }

    private double makeSumOfAmounts(List<Transaction> transactions) {
        double result = 0;
        for (Transaction transaction: transactions) {
            result += transaction.amount;
        }
        return result;
    }
}
