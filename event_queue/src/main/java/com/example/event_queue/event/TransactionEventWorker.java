package com.example.event_queue.event;

import com.example.event_queue.domain.entity.TransactionInfo;
import com.example.event_queue.domain.entity.TransactionStatus;
import com.example.event_queue.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이벤트를 처리할 워커
 */
@Slf4j
@RequiredArgsConstructor
public class TransactionEventWorker implements Runnable {

  /**
   * 처리 flow 1. 큐 사이즈가 0보다 작다면 큐에 처리할 이벤트가 없다는 의미이므로 아무것도 하지 않는다. 2. 큐 사이즈가 0보다 크다면 큐에서 이벤트를 꺼낸 후
   * 이벤트에서 결제 거래 정보를 가져온다. 3. 데이터베이스 속 결제 거래의 상태를 PROGRESS로 업데이트 한다. 4. 모종의 처리를 한다. 여기서는 이 처리가
   * 1초(1000ms) 걸린다고 가정하였다. 5. 처리가 50:50 화률로 성공, 혹은 실패된다. 이 때 결과에 따라 데이터베이스 속 결제거래의 상태를 SUCCESS 혹은
   * FAILURE로 업데이트한다. 6. 작업을 종료한다.(이 때 여기서 후속 처리를 진행해도 좋다. 7. 3 ~ 6 작업중 예외가 발생한다면 즉시 결제 거래의 상태를
   * FAULURE로 업데이트하고 작업을 종료한다.
   */

  private final TransactionEventQueue eventQueue;
  private final TransactionService transactionService;


  @Transactional
  @Override
  public void run() {
    if (eventQueue.isRemaining()) {
      TransactionInfo transactionInfo = eventQueue.poll();
      try {
        transactionInfo = updateStatus(transactionInfo, TransactionStatus.PROGRESS);
        processing(1_000);
        successOfFailure(transactionInfo);
      } catch (Exception e) {
        handlingInCaseOfFailure(transactionInfo);
      }
    }

  }

  private void processing(int processTimeInMs) {
    try {
      Thread.sleep(processTimeInMs);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void successOfFailure(TransactionInfo transactionInfo) {
    if (Math.random() < 0.5) {
      updateStatus(transactionInfo, TransactionStatus.SUCCESS);
      return;
    }
    updateStatus(transactionInfo, TransactionStatus.FAILURE);
  }

  private void handlingInCaseOfFailure(TransactionInfo transactionInfo) {
    updateStatus(transactionInfo, TransactionStatus.FAILURE);
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
