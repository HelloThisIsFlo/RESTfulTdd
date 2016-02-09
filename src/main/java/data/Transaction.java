package data;

import java.util.Objects;

/**
 * Temporary Transaction created to test the Json parser / serializer.
 * Once Json is functional the original Transaction DTO will be transformed to this one.
 */
public class Transaction {

    public final double amount;
    public final String type;
    public final long parentId;

    public Transaction(double amount, String type, long parentId) {
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                parentId == that.parentId &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, type, parentId);
    }
}
