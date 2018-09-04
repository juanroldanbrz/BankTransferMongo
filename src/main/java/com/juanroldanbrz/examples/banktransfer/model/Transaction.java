package com.juanroldanbrz.examples.banktransfer.model;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.jongo.marshall.jackson.oid.MongoObjectId;

@Getter
@Setter
public class Transaction {

  @MongoObjectId
  private String _id;

  private String senderAccountId;
  private String destinationAccountId;
  private double amount;
  private TransactionState transactionState;
  private Instant createdAt;
  private Instant updatedAt;

}
