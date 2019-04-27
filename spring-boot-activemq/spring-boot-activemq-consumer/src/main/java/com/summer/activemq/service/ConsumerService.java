package com.summer.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@Component
@Slf4j
public class ConsumerService {

  @Autowired
  private AsyncService asyncService;
  @Autowired
  private ThreadPoolTaskExecutor asyncTaskExecutor;
  //@JmsListener(destination = "summer.topic", containerFactory= "jmsListenerContainerTopic")
  public void receiveTopic(String text){
    log.info("Topic Consumer1: "+ text);
  }

  @JmsListener(destination = "summer.queue", containerFactory= "jmsListenerContainerQueue")
  public void receiveQueue(String text){
    int activeCount  = asyncTaskExecutor.getActiveCount();
    int poolSize = asyncTaskExecutor.getPoolSize();
    log.info("activeCount: " + activeCount +", poolSize：" + poolSize);
    asyncService.receive(text);
  }

}
