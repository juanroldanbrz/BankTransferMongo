package com.juanroldanbrz.examples.banktransfer.model;

import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.jongo.marshall.jackson.oid.MongoObjectId;


@Getter
@Setter
public class Account {

  @MongoObjectId
  private String _id;

  private String owner;
  private Double balance;
  private List<String> pendingTransactions;

  private Instant createdAt;
  private Instant updatedAt;

}
