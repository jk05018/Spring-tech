package com.example.event.model.event;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FileEvent {

  private String eventId;
  private EventType eventType;
  private Map<String, Object> dataMap;

  public static FileEvent complete(final Map<String, Object> dataMap) {
    return FileEvent.builder()
        .eventId(UUID.randomUUID().toString())
        .eventType(EventType.COMPLETE)
        .dataMap(dataMap)
        .build();
  }

  public static FileEvent error(final Map<String, Object> dataMap) {
    return FileEvent.builder()
        .eventId(UUID.randomUUID().toString())
        .eventType(EventType.ERROR)
        .dataMap(dataMap)
        .build();
  }

  public enum EventType {
    COMPLETE, ERROR
  }

}
