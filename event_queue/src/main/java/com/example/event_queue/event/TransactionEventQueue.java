package com.example.event_queue.event;

import com.example.event_queue.domain.entity.TransactionInfo;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * 결제 거래 이벤트가 대기할 큐
 */
@Slf4j
public class TransactionEventQueue {

  private final Queue<TransactionInfo> queue;
  private final int queueSize;

  private TransactionEventQueue(final int queueSize) {
    this.queueSize = queueSize;
    this.queue = new LinkedBlockingQueue<>(queueSize);
  }

  public static TransactionEventQueue of(final int size) {
    return new TransactionEventQueue(size);
  }

  public boolean offer(final TransactionInfo transaction) {
    boolean returnValue = queue.offer(transaction);
    healthCheck();
    return returnValue;
  }

  public TransactionInfo poll() {
    if (queue.size() <= 0) {
      throw new IllegalStateException("큐에 이벤트가 존재하지 않습니다.");
    }
    final TransactionInfo transaction = queue.poll();
    healthCheck();
    return transaction;
  }

  public boolean isFull() {
    return queue.size() == queueSize;
  }

  public boolean isRemaining() {
    return queue.size() > 0;
  }

  // 현재 상태 check
  private void healthCheck() {
    log.info("{\"totalQueueSize\":{}, \"currentQueueSize\":{}}", queueSize, queue.size());
  }

}
