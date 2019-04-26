package com.summer.activemq.task;

import javax.jms.Queue;
import javax.jms.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@Component
@Slf4j
@EnableScheduling
public class ProducerTask {

  @Autowired
  private JmsMessagingTemplate jmsMessagingTemplate;
  @Autowired
  private Topic topic;
  @Autowired
  private Queue queue;
  private static int count= 0;

  @Scheduled(fixedDelay = 1000)
  public void send() {
    log.info("message send " + count);
    this.jmsMessagingTemplate.convertAndSend(this.queue, "hi.activeMQ,index=" + count);
    this.jmsMessagingTemplate.convertAndSend(this.topic, "hi,activeMQ( topic )，index=" + count++);
  }
}
