package com.juanroldanbrz.examples.banktransfer.repository;

import com.juanroldanbrz.examples.banktransfer.model.Account;
import com.juanroldanbrz.examples.banktransfer.model.Transaction;

public interface AccountRepository {

  Account create(Account account);

  Account findById(String accountId);

  Account linkTransactionAndActWithBalance(String accountId, Transaction transaction);

  Account unlinkTransaction(String accountId, Transaction transaction);

  void clear();

}
