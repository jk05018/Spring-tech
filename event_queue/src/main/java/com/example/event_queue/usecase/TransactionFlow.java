package com.example.event_queue.usecase;

import com.example.event_queue.domain.entity.TransactionInfo;
import com.example.event_queue.domain.service.TransactionService;
import com.example.event_queue.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionFlow {

  /**
   * 다음의 flow를 거침
   * 1. 사용자가 결제를 요청
   * 2. 컨트롤러가 결제 요청을 받음
   * 3. 사용자 요청의 유효성 검증
   * 4. StandBy 상태의 유효한 결제 거래 생성
   * 5. 결제 거래를 데이터베이스에 저장
   * 6. 이벤트 퍼블리셔가 결제 거래 이벤트를 퍼블리싱
   * 7. 사용자에게 결제 거래 요청에 대한 응답을 보내고 HTTP통신을 종료
   * 8. 발생된 결제 거래 이벤트를 이벤트 리스너가 감지하고 결제 거래 이벤트에서 결제 거래 정보를 추출
   * 9. 큐가 꽉 차 있지 않다면 결제 거래 정보를 큐에 입력하고 결제 거래의 상태를 QUEUE로 데이터베이스 업데이트
   * 10. 큐가 꽉 차 있다면 상태를 QUEUE_WAIT로 데이터베이스 업데이트
   * 11. 백그라운드 스레드에서 큐에 있는 결제 거래들을 처리하고 상태를 SUCCESS 혹은 FAILURE로 업데이트
   * 12. 결제 거래 완료에 대한 후처리를 진행(ex. 결제 결과를 사용자의 앱으로 푸쉬)
   *
   */

  private final EventPublisher eventPublisher;
  private final TransactionService transactionService;

  public TransactionInfo save(final TransactionInfo transactionInfo) {
    TransactionInfo info = transactionService.save(transactionInfo);
    log.info("Create new Transaction {}", transactionInfo);
    eventPublisher.publish(info);
    return info;
  }

}
