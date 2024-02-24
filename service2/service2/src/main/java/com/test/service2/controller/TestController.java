package com.test.service2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @RequestMapping("/service2")
  public String a() {
    return "Chuyển tới service 2";
  }
}