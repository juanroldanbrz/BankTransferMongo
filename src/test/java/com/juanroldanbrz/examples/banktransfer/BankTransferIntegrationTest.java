package com.juanroldanbrz.examples.banktransfer;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.juanroldanbrz.examples.banktransfer.model.Account;
import com.juanroldanbrz.examples.banktransfer.model.Transaction;
import com.juanroldanbrz.examples.banktransfer.model.TransactionState;
import com.juanroldanbrz.examples.banktransfer.service.AccountServiceImpl;
import com.juanroldanbrz.examples.banktransfer.service.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankTransferIntegrationTest {

  @Autowired
  private AccountServiceImpl accountService;

  @Autowired
  private TransactionServiceImpl transactionService;

  @Test
  public void moneyTransferIsCompleted() {
    //Arrange
    Double initialBalanceSender = 10000.50d;
    double initialBalanceReceiver = 0.50d;
    double balanceToTransfer = 57.2d;

    Account sender = new Account();
    Account receiver = new Account();

    sender.setBalance(initialBalanceSender);
    sender.setOwner("Anonymous user");

    receiver.setBalance(initialBalanceReceiver);
    receiver.setOwner("Juan Bermudez");

    accountService.createAccount(sender);
    accountService.createAccount(receiver);

    //Act
    accountService.transferAmount(sender.get_id(), receiver.get_id(), balanceToTransfer);

    sender = accountService.findById(sender.get_id());
    receiver = accountService.findById(receiver.get_id());

    Transaction completedTransaction = transactionService.findBySenderAccountId(sender.get_id());

    //Assert
    assertEquals("Sender has 57.2 less", initialBalanceSender - balanceToTransfer,
        sender.getBalance());
    assertEquals("Receiver has 57.2 more", initialBalanceReceiver + balanceToTransfer,
        receiver.getBalance());
    assertEquals("Transaction is in DONE state", TransactionState.DONE,
        completedTransaction.getTransactionState());
  }

}
