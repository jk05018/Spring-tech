package com.example.event_queue.controller;

import com.example.event_queue.domain.entity.TransactionInfo;
import com.example.event_queue.usecase.TransactionFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {

  private final TransactionFlow transactionFlow;

  @GetMapping("/transaction")
  public String transaction() {
    final TransactionInfo transactionInfo = transactionFlow.save(TransactionInfo.create());
    return "transactionId = " + transactionInfo.getId();
  }

  @GetMapping("/transactions")
  public String transactions() {
    for (int i = 0; i < 50; ++i) {
      transactionFlow.save(TransactionInfo.create());
    }
    return "success";
  }

}
