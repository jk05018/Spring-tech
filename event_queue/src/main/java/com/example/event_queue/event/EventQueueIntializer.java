package com.example.event_queue.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueIntializer {

  @Bean
  public TransactionEventQueue transactionEventQueue() {
    return TransactionEventQueue.of(1_000);
  }

}
