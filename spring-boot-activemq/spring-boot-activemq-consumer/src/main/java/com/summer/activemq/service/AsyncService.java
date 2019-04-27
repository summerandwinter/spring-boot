package com.summer.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/27
 */
@Service
@Slf4j
public class AsyncService {

  @Async
  public void receive(String message) {
    log.info("Queue consumer start : " + message);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("Queue consumer end   : " + message);
  }

}
