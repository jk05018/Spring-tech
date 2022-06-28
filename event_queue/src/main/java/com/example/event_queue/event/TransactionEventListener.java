package com.example.event_queue.event;

import com.example.event_queue.domain.entity.TransactionInfo;
import com.example.event_queue.domain.entity.TransactionStatus;
import com.example.event_queue.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * TransactionEvent가 publishing 되면 어떤 처리를 담당할 이벤트 리스너
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventListener {

  /**
   * 처리 Flow
   * <p>
   * 1. TransactionEventQueue에 이벤트를 집어 넣어 본다. 2. true가 반환된다면 데이터베이스 속 결제 거래 상태를 QUEUE로 변경하고 작업을
   * 종료한다. 3. false가 반환된다면 true가 반환될 때까지 계속해서 큐에 이벤트를 집어넣어본다. (=큐에 공간이 남을 때까지) 4. 이 때 이벤트의 상태가
   * QUEUE_WAIT가 아니라면(=STAND BY) QUEUE_WAIT로 데이터베이스를 업데이트한다. 조건문이 있는 이유는 업데이트 쿼리가 계속해서 발생하지 ㅇ낳도록 학
   * ㅣ위함이다.
   */

  private final TransactionEventQueue eventQueue;
  private final TransactionService transactionService;

  @EventListener
  public void onEvent(TransactionInfo transaction) {
    if (!transaction.isStandBy()) {
      log.info("Transaction(id:{}) status is not STANDBY", transaction.getId());
      return;
    }

    while (eventQueue.isFull()) {
      if (!transaction.isQueueWait()) {
        transaction = updateStatus(transaction, TransactionStatus.QUEUE_WAIT);
      }
    }
    transaction = updateStatus(transaction, TransactionStatus.QUEUE);
    eventQueue.offer(transaction);

  }

  private TransactionInfo updateStatus(TransactionInfo updateTransactionInfo,
      TransactionStatus status) {
    final TransactionStatus beforeStatus = updateTransactionInfo.getStatus();
    final TransactionInfo updateStatus = updateTransactionInfo.update(status);
    log.info("{\"transactionId\":{}, \"before\":{}, \"after\":{}}", updateTransactionInfo.getId(),
        beforeStatus, updateStatus.getStatus());

    return transactionService.update(updateTransactionInfo);
  }

}
