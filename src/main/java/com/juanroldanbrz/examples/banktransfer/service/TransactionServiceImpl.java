package com.juanroldanbrz.examples.banktransfer.service;

import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import com.juanroldanbrz.examples.banktransfer.model.TransactionState;
import com.juanroldanbrz.examples.banktransfer.repository.TransactionRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepositoryImpl transactionRepository;

  @Autowired
  public TransactionServiceImpl(TransactionRepositoryImpl transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction create(String senderAccountId, String destinationAccountId, Double amount) {
    Transaction transaction = new Transaction();
    transaction.setSenderAccountId(senderAccountId);
    transaction.setDestinationAccountId(destinationAccountId);
    transaction.setAmount(amount);
    transaction.setTransactionState(TransactionState.INITIAL);
    return transactionRepository.create(transaction);
  }

  @Override
  public Transaction changeState(String transactionId, TransactionState transactionState) {
    return transactionRepository.changeState(transactionId, transactionState);
  }

  @Override
  public Transaction findBySenderAccountId(String senderAccountId) {
    return transactionRepository.findBySenderAccountId(senderAccountId);
  }

  @Override
  public void clear() {
    transactionRepository.clear();
  }
}
