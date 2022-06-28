package com.example.event_queue.event;

import com.example.event_queue.domain.entity.TransactionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 이벤트 객체가 생성되면 이를 publishing 해주는 역할
 */
@Component
@RequiredArgsConstructor
public class EventPublisher {

  private final ApplicationEventPublisher publisher;

  public void publish(final TransactionInfo transactionInfo) {
    publisher.publishEvent(transactionInfo);
  }

}
