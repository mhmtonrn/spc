package io.mhmtonrn.spc.controller;

import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import io.mhmtonrn.spc.service.SpringCliService;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/create-app")
@RequiredArgsConstructor
public class SpringCliApi {
  private final SpringCliService springCliService;

  @PostMapping
  public String createApp(@RequestBody CreateAppDTO createAppDTO) throws IOException, InterruptedException {
    return springCliService.createApp(createAppDTO);
  }

}
