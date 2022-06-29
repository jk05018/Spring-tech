성package com.example.event.controller;

import com.example.event.service.FileService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileApiController {

  private final FileService fileService;

  @GetMapping("/files/{id}")
  public ResponseEntity<?> upload(@PathVariable final long id){
    fileService.fileUpload(Map.of("id", id, "type", "image", "size", "5MB"));
    return ResponseEntity.ok(null);
  }

}
