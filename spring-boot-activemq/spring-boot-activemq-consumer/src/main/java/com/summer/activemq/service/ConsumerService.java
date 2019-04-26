package com.summer.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@Component
@Slf4j
public class ConsumerService {
  //@JmsListener(destination = "summer.topic", containerFactory= "jmsListenerContainerTopic")
  public void receiveTopic(String text){
    log.info("Topic Consumer1: "+ text);
  }

  //@JmsListener(destination = "summer.topic", containerFactory= "jmsListenerContainerTopic")
  public void receiveTopic2(String text){
    log.info("Topic Consumer2: "+ text);
  }

  @Async
  @JmsListener(destination = "summer.queue", containerFactory= "jmsListenerContainerQueue")
  public void receiveQueue(String text){
    log.info("Queue Consumer start : " + text);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("Queue Consumer end: "+ text);
  }

  //@JmsListener(destination = "summer.queue", containerFactory= "jmsListenerContainerQueue")
  public void receiveQueue2(String text){
    log.info("Queue Consumer2: "+ text);
  }

}
