package io.summer.springboot.rabbitmq.producer.task;

import io.summer.springboot.common.constant.MessageQueue;
import io.summer.springboot.common.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author summer
 */
@Component
@Slf4j
public class ProducerTask {

  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public ProducerTask(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Scheduled(fixedDelay = 1000)
    public void send() {
      MessageVO message = new MessageVO("message");
      rabbitTemplate.convertAndSend(MessageQueue.RABBIT_MQ_TOPIC_EXCHANGE_NAME, "topic.message", message);
      MessageVO log = new MessageVO("log");
      rabbitTemplate.convertAndSend(MessageQueue.RABBIT_MQ_TOPIC_EXCHANGE_NAME, "topic.log", log);

  }

}
