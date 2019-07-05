package io.summer.springboot.oauth2.resources.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author summerandwinter
 * @date 2019/7/2
 */
@RestController("/api")
public class HelloController {

  @PostMapping("/api/hi")
  public String say(String name) {
    return "hi , " + name;
  }

}
