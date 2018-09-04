package com.juanroldanbrz.examples.banktransfer.service;

import com.juanroldanbrz.examples.banktransfer.model.Account;
import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import com.juanroldanbrz.examples.banktransfer.model.TransactionState;
import com.juanroldanbrz.examples.banktransfer.repository.AccountRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepositoryImpl accountRepository;
  private final TransactionServiceImpl transactionService;

  @Autowired
  public AccountServiceImpl(final AccountRepositoryImpl accountRepository, final TransactionServiceImpl transactionService) {
    this.accountRepository = accountRepository;
    this.transactionService = transactionService;
  }

  public Account createAccount(Account account) {
    return accountRepository.create(account);
  }

  @Override
  public Account findById(String accountId){
    return accountRepository.findById(accountId);
  }

  @Override
  public void transferAmount(String senderAccountId, String targetAccountId, Double amount) {
    Transaction transaction = transactionService.create(senderAccountId, targetAccountId, amount);

    String transactionId = transaction.get_id();
    transactionService.changeState(transactionId, TransactionState.PENDING);

    accountRepository.linkTransactionAndActWithBalance(senderAccountId, transaction);
    accountRepository.linkTransactionAndActWithBalance(targetAccountId, transaction);

    transactionService.changeState(transactionId, TransactionState.COMMITED);

    accountRepository.unlinkTransaction(senderAccountId, transaction);
    accountRepository.unlinkTransaction(targetAccountId, transaction);

    transactionService.changeState(transactionId, TransactionState.DONE);
  }

  @Override
  public void clear() {
    accountRepository.clear();
  }
}
