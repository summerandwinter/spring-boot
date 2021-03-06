package io.summer.springboot.activemq.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Handle message queue.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/27
 */
@Service
@Slf4j
public class AsyncService {

  /**
   * Print the message get from message queue.
   * @param message String
   */
  @Async
  public void receive(String message) {
    log.trace("Queue consumer start : " + message);
    log.debug("Queue consumer start : " + message);
    log.info("Queue consumer start : " + message);
    log.warn("Queue consumer start : " + message);
    log.error("Queue consumer start : " + message);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //log.info("Queue consumer end   : " + message);
  }

}
