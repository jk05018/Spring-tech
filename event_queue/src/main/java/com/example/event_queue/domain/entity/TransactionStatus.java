package com.example.event_queue.domain.entity;

public enum TransactionStatus {
  STANDBY,
  QUEUE_WAIT,
  QUEUE,
  PROGRESS,
  SUCCESS,
  FAILURE
}
