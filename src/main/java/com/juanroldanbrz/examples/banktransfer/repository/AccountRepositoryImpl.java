package com.juanroldanbrz.examples.banktransfer.repository;

import com.juanroldanbrz.examples.banktransfer.exception.ServiceException;
import com.juanroldanbrz.examples.banktransfer.model.Account;
import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AccountRepositoryImpl implements AccountRepository{

  private static final String COLLECTION_NAME = "account";

  private final MongoCollection collection;

  public AccountRepositoryImpl(final Jongo jongo) {
    collection = jongo.getCollection(COLLECTION_NAME);
  }

  @Override
  public Account create(Account account) {
    Instant now = Instant.now();

    account.setCreatedAt(now);
    account.setUpdatedAt(now);

    collection.save(account);
    return account;
  }

  @Override
  public Account findById(String accountId) {
    return collection.findOne(new ObjectId(accountId)).as(Account.class);
  }

  @Override
  public Account linkTransactionAndActWithBalance(String accountId, Transaction transaction) {
    Instant now = Instant.now();
    String senderAccountId = transaction.getSenderAccountId();
    String destinationAccountId = transaction.getDestinationAccountId();
    double amount = transaction.getAmount();

    if (StringUtils.equals(accountId, senderAccountId)) {
      amount = -amount;
    } else if (StringUtils.equals(accountId, destinationAccountId)) {
      amount = +amount;
    } else {
      throw new ServiceException("Account does not belong to the transaction. (transactionId: "
          + transaction.get_id() + ", accountId: " + accountId + ")");
    }

    String transactionId = transaction.get_id();

    collection.update("{_id: #, pendingTransactions: {$ne: #}}", new ObjectId(accountId), transactionId)
        .with("{$inc: {balance: #}, $push: {pendingTransactions: #}, $set: {updatedAt: #}}",
            amount, transactionId, now);

    return findById(accountId);
  }

  @Override
  public Account unlinkTransaction(String accountId, Transaction transaction) {
    String transactionId = transaction.get_id();

    if (!StringUtils.equals(accountId, transaction.getSenderAccountId()) &&
        !StringUtils.equals(accountId, transaction.getDestinationAccountId())) {

      throw new ServiceException("Account does not belong to the transaction. (transactionId: "
          + transactionId + ", accountId: " + accountId + ")");
    }

    collection.update(new ObjectId(accountId))
        .with("{$pull: {pendingTransactions: #}}", transactionId);
    return findById(accountId);
  }

  @Override
  public void clear() {
    collection.remove();
  }
}
