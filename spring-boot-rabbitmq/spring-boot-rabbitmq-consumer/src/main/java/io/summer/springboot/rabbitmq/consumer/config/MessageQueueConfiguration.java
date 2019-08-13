package io.summer.springboot.rabbitmq.consumer.config;

import io.summer.springboot.common.constant.MessageQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author summer
 */
@Configuration
public class MessageQueueConfiguration {

  @Bean
  public Queue queue() {
    return new Queue(MessageQueue.RABBIT_MQ_QUEUE_NAME);
  }

}
