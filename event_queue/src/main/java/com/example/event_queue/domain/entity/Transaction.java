package com.example.event_queue.domain.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private TransactionStatus status;

  public static Transaction from(final TransactionInfo transactionInfo) {

    return new Transaction(transactionInfo.getId(), transactionInfo.getStatus());
  }

  public TransactionInfo toTransactionInfo() {
    return TransactionInfo.of(id, status);
  }

  public Transaction update(final TransactionInfo transactionInfo) {
    this.status = transactionInfo.getStatus();

    return this;
  }

}
