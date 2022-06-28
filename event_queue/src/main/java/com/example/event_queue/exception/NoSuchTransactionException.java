package com.example.event_queue.exception;

public class NoSuchTransactionException extends RuntimeException {

  private static final String MESSAGE = "해당하는 트랜잭션이 존재하지 않습니다.";

  public NoSuchTransactionException() {
    super();
  }

  public NoSuchTransactionException(Throwable cause) {
    super(cause);
  }
}
