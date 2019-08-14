package io.summer.springboot.rabbitmq.consumer.service;

import io.summer.springboot.common.constant.MessageQueue;
import io.summer.springboot.common.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author summer
 */
@Service
@Slf4j
public class ConsumerService {

  @RabbitListener(queues = MessageQueue.RABBIT_MQ_TOPIC_MESSAGE_QUEUE_NAME)
  public void receiveMessage(MessageVO message) {
    log.info("receive message id: {}, message: {}", message.getId(),  message.getMessage());
  }

  @RabbitListener(queues = MessageQueue.RABBIT_MQ_TOPIC_LOG_QUEUE_NAME)
  public void receiveLog(MessageVO message) {
    log.info("receive log id: {}, message: {}", message.getId(),  message.getMessage());
  }

}
