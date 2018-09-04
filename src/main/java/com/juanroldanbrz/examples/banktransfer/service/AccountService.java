package com.juanroldanbrz.examples.banktransfer.service;

import com.juanroldanbrz.examples.banktransfer.model.Account;

public interface AccountService {

  Account findById(String accountId);

  void transferAmount(String senderAccountId, String targetAccountId, Double amount);

  void clear();
}
