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

    @Before
    public void setUp() throws Exception {
        storage = new InMemoryStorageImpl();
        transactionWithIds = new ArrayList<>(5);
        transactionWithIds.add(new TransactionWithId(656565L, new Transaction(534.3542, "car", 454684)));
        transactionWithIds.add(new TransactionWithId(4445L, new Transaction(3121.1, "boat", 366541)));
        transactionWithIds.add(new TransactionWithId(99789L, new Transaction(54.23, "truck", 12)));
        transactionWithIds.add(new TransactionWithId(6677L, new Transaction(986.4, "pen", 3784534534534L)));
        transactionWithIds.add(new TransactionWithId(321644889L, new Transaction(6554.5, "thing", 53453)));
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
        Transaction transaction = transactionWithIds.get(1).transaction;
        long id = transactionWithIds.get(1).transactionId;
        storage.save(transaction, id);

        assertTrue(storage.isPresent(id));
        Transaction result = storage.get(id);

        assertEquals(result, transaction);
    }
}