package data;

import data.inmemory.InMemoryStorageImpl;
import data.inmemory.TransactionWithId;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InMemoryStorageImplTest {

    Storage storage;
    List<TransactionWithId> transactionWithIds;
    private static final Transaction TEST_TRANSACTION = new Transaction(351531.534, "type", 654645645465L);
    private static final long TEST_TRANSACTION_ID = 351351153L;
    private static final long PARENT_ID = 658465484685L;

    private static <T> void assertListHaveSameElements(List<T> list1, List<T> list2) {
        try {
            assertTrue(list1 != null && list2 != null &&list1.containsAll(list2) && list2.containsAll(list1));
        } catch (AssertionError e) {
            throw new AssertionError("\nExpected (In any order) :" + toStringEvenIfNull(list1) + "\nActual :" + toStringEvenIfNull(list2));
        }
    }

    private static String toStringEvenIfNull(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return "null";
        }
    }

    @Before
    public void setUp() throws Exception {
        storage = new InMemoryStorageImpl();
        transactionWithIds = new ArrayList<>(5);
        transactionWithIds.add(new TransactionWithId(656565L, new Transaction(534.3542, "car", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(4445L, new Transaction(3121.1, "boat", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(99789L, new Transaction(54.23, "truck", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(6677L, new Transaction(986.4, "boat", PARENT_ID)));
        transactionWithIds.add(new TransactionWithId(321644889L, new Transaction(6554.5, "boat", PARENT_ID)));
    }

    @Test (expected = TransactionNotSavedException.class)
    public void saveNullTransaction_throwException() throws Exception {
        storage.save(null, 6354L);
    }

    @Test (expected = TransactionNotSavedException.class)
    public void saveIdZero_throwException() throws Exception {
        Transaction transaction = transactionWithIds.get(1).transaction;
        storage.save(transaction, 0);
    }

    @Test
    public void saveTransaction_present_getTransaction() throws Exception {
        storage.save(TEST_TRANSACTION, TEST_TRANSACTION_ID);

        assertTrue(storage.isPresent(TEST_TRANSACTION_ID));
        Transaction result = storage.get(TEST_TRANSACTION_ID);

        assertEquals(result, TEST_TRANSACTION);
    }

    @Test
    public void getListOfTypes() throws Exception {
        for (TransactionWithId transactionWithId : transactionWithIds) {
            storage.save(transactionWithId.transaction, transactionWithId.transactionId);
        }
        List<Long> expectedTransactionsWithBoatType = new ArrayList<>(3);
        expectedTransactionsWithBoatType.add(transactionWithIds.get(1).transactionId);
        expectedTransactionsWithBoatType.add(transactionWithIds.get(3).transactionId);
        expectedTransactionsWithBoatType.add(transactionWithIds.get(4).transactionId);

        String type = "boat";
        List<Long> resultTransactionsWithBoatType = storage.getFromType(type);

        assertListHaveSameElements(expectedTransactionsWithBoatType, resultTransactionsWithBoatType);
    }

    @Test (expected = TransactionNotFoundException.class)
    public void transactionNotFound_throwException() throws Exception {
        storage.get(TEST_TRANSACTION_ID);
    }

    @Test
    public void getTransactionsFromParentId() throws Exception {
        List<Transaction> expected = new ArrayList<>(5);
        for (TransactionWithId transactionWithId: transactionWithIds) {
            storage.save(transactionWithId.transaction, transactionWithId.transactionId);
            expected.add(transactionWithId.transaction);
        }

        List<Transaction> result = storage.getFromParentId(PARENT_ID);

        assertListHaveSameElements(expected, result);
    }

}