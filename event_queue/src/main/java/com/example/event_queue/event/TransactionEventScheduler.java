package com.example.event_queue.event;

import com.example.event_queue.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 큐를 풀링할 스케쥴러
 */
@Component
@RequiredArgsConstructor
public class TransactionEventScheduler {

  private final TransactionEventQueue eventQueue;
  private final TransactionService transactionService;

  @Async("taskScheduler")
  @Scheduled(fixedRate = 100)
  public void schedule() {
    new TransactionEventWorker(eventQueue, transactionService)
        .run();
  }
}
