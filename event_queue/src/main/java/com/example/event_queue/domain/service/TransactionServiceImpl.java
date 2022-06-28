package com.example.event_queue.domain.service;

import com.example.event_queue.domain.entity.Transaction;
import com.example.event_queue.domain.entity.TransactionInfo;
import com.example.event_queue.domain.repository.TransactionRepository;
import com.example.event_queue.exception.NoSuchTransactionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionServiceImpl implements
    TransactionService {

  private final TransactionRepository transactionRepository;

  @Transactional
  @Override
  public TransactionInfo save(final TransactionInfo transactionInfo) {
    final Transaction transaction = transactionRepository.save(Transaction.from(transactionInfo));

    System.out.println(transaction.getId());
    return transaction.toTransactionInfo();
  }

  @Transactional
  @Override
  public TransactionInfo update(final TransactionInfo transactionInfo) {
    Transaction transaction = transactionRepository.findById(transactionInfo.getId())
        .orElseThrow(NoSuchTransactionException::new);

    transaction.update(transactionInfo);

    return transaction.toTransactionInfo();
  }
}
