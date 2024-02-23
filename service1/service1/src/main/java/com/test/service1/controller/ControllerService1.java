package com.test.service1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerService1 {
  @RequestMapping("/service1")
  public String service1(){
    return "Chuyển tới service1";
  }
}
