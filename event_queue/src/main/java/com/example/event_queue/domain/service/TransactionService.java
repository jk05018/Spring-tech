package com.example.event_queue.domain.service;

import com.example.event_queue.domain.entity.TransactionInfo;

public interface TransactionService {

  TransactionInfo save(final TransactionInfo transaction);

  TransactionInfo update(final TransactionInfo transaction);

}
