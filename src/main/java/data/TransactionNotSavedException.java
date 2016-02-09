package data;

public class TransactionNotSavedException extends Exception {

    public TransactionNotSavedException() {
    }

    public TransactionNotSavedException(String message) {
        super(message);
    }

    public TransactionNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionNotSavedException(Throwable cause) {
        super(cause);
    }

    public TransactionNotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
