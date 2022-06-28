package com.example.event_queue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableAsync
@EnableScheduling
public class ThreadConfig {

  @Bean
  public ThreadPoolTaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    // 스레큘러의 스레드풀의 사이즈
    threadPoolTaskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());

    // 로그에 찍힐 스케쥴러 스레드의 접두사
    threadPoolTaskScheduler.setThreadNamePrefix("Scheduler-Thread-");

    // 모든 설정을 적용하고, ThreadPoolTaskScheduler 초기화
    threadPoolTaskScheduler.initialize();

    return threadPoolTaskScheduler;
  }

}
