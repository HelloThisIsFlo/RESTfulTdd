package data;

/**
 * Temporary Transaction created to test the Json parser / serializer.
 * Once Json is functional the original Transaction DTO will be transformed to this one.
 */
public class TransactionTemp {

    public final double amount;
    public final String type;
    public final long parentId;

    public TransactionTemp(double amount, String type, long parentId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }
}
