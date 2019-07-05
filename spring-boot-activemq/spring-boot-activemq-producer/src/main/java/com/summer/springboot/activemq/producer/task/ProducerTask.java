package com.summer.springboot.activemq.producer.task;

import javax.jms.Queue;
import javax.jms.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Message queue producer.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@Component
@Slf4j
@EnableScheduling
public class ProducerTask {

  private final JmsMessagingTemplate jmsMessagingTemplate;
  private final Topic topic;
  private final Queue queue;
  private int count = 0;

  /**
   * Inject beans in the construct method.
   * @param topic Topic
   * @param queue Queue
   * @param jmsMessagingTemplate JmsMessagingTemplate
   */
  @Autowired
  public ProducerTask(Topic topic, Queue queue, JmsMessagingTemplate jmsMessagingTemplate) {
    this.topic = topic;
    this.queue = queue;
    this.jmsMessagingTemplate = jmsMessagingTemplate;
  }

  /**
   * Send a message to activemq queue and topic per sec.
   */
  @Scheduled(fixedDelay = 1000)
  public void send() {
    log.info("message send " + count);
    this.jmsMessagingTemplate.convertAndSend(this.queue, "hi.activeMQ,index=" + count);
    this.jmsMessagingTemplate.convertAndSend(this.topic, "hi,activeMQ( topic )，index=" + count++);
  }
}
