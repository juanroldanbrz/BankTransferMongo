package com.juanroldanbrz.examples.banktransfer.service;

import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import com.juanroldanbrz.examples.banktransfer.model.TransactionState;

public interface TransactionService {

  Transaction create(String senderAccountId, String destinationAccountId, Double amount);

  Transaction changeState(String transactionId, TransactionState transactionState);

  Transaction findBySenderAccountId(String senderAccountId);

  public void clear();
}
