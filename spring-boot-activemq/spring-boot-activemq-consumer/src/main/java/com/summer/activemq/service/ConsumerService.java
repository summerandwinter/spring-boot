package com.summer.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Consumer service for message queue.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@Component
@Slf4j
public class ConsumerService {

  private final AsyncService asyncService;
  private final ThreadPoolTaskExecutor asyncTaskExecutor;

  @Autowired
  public ConsumerService(AsyncService asyncService, ThreadPoolTaskExecutor asyncTaskExecutor) {
    this.asyncService = asyncService;
    this.asyncTaskExecutor = asyncTaskExecutor;
  }

  //@JmsListener(destination = "summer.topic", containerFactory= "jmsListenerContainerTopic")
  public void receiveTopic(String text) {
    log.info("Topic Consumer1: " + text);
  }

  /**
   * Get message from queue.
   * @param text String
   */
  @JmsListener(destination = "summer.queue", containerFactory = "jmsListenerContainerQueue")
  public void receiveQueue(String text) {
    int activeCount  = asyncTaskExecutor.getActiveCount();
    int poolSize = asyncTaskExecutor.getPoolSize();
    log.info("activeCount: " + activeCount + ", poolSize：" + poolSize);
    asyncService.receive(text);
  }

}
