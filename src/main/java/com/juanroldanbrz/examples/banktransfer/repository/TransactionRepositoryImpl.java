package com.juanroldanbrz.examples.banktransfer.repository;

import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import com.juanroldanbrz.examples.banktransfer.model.TransactionState;
import com.mongodb.MongoException;
import java.time.Instant;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

  private static final String COLLECTION_NAME = "transaction";

  private final MongoCollection collection;

  public TransactionRepositoryImpl(final Jongo jongo) {
    collection = jongo.getCollection(COLLECTION_NAME);
  }

  @Override
  public Transaction create(Transaction transaction) {
    Instant now = Instant.now();

    transaction.setCreatedAt(now);
    transaction.setUpdatedAt(now);
    collection.save(transaction);
    return transaction;
  }

  @Override
  public Transaction findById(String transactionId) {
    return collection.findOne(new ObjectId(transactionId)).as(Transaction.class);
  }

  @Override
  public Transaction changeState(String transactionId, TransactionState state) {
    Instant now = Instant.now();

    int affectedDocuments = collection.update(new ObjectId(transactionId))
        .with("{$set: {transactionState: #, updatedAt: #}}", state, now)
        .getN();

    if (affectedDocuments == 0) {
      throw new MongoException("Cannot change the state of the transaction: " + transactionId);
    } else {
      return findById(transactionId);
    }
  }

  @Override
  public Transaction findBySenderAccountId(String senderAccountId) {
    return collection.findOne("{senderAccountId: #}", senderAccountId).as(Transaction.class);
  }

  @Override
  public void clear() {
    collection.remove();
  }
}
