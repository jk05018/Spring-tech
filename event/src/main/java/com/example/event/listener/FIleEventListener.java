package com.example.event.listener;

import com.example.event.model.event.FileEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FIleEventListener {

  @EventListener
  public void receive(final FileEvent fileEvent){
    log.info("file event received : {}", fileEvent);
  }

}
