package com.example.event_queue.domain.entity;

import lombok.Value;

/**
 * 사용자 요청이 유효하다면 생성시킬 결재 거래 객체 결제 거래를 표현하는 클래스?
 */
@Value(staticConstructor = "of")
public class TransactionInfo {

  Long id;
  TransactionStatus status;

  public static TransactionInfo create() {
    return TransactionInfo.of(null, TransactionStatus.STANDBY);
  }

  public TransactionInfo update(TransactionStatus status) {
    return TransactionInfo.of(id, status);
  }

  public boolean isStandBy() {
    return status == TransactionStatus.STANDBY;
  }

  public boolean isQueueWait() {
    return status == TransactionStatus.QUEUE_WAIT;
  }

}
