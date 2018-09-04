package com.juanroldanbrz.examples.banktransfer.repository;

import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import com.juanroldanbrz.examples.banktransfer.model.TransactionState;

public interface TransactionRepository {

  Transaction create(Transaction transaction);

  Transaction findById(String transactionId);

  Transaction changeState(String transactionId, TransactionState state);

  Transaction findBySenderAccountId(String senderAccountId);

  void clear();

}
