package io.summer.springboot.oauth2.resources.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof OAuth2Authentication) {
      OAuth2Authentication oauth = (OAuth2Authentication) authentication;
      name = oauth.getPrincipal().toString();
      System.out.println(oauth.getPrincipal().toString());
    }
    return "hi , " + name;
  }

}
