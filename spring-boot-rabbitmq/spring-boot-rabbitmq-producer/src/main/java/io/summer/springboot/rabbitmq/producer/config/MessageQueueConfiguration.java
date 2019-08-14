package io.summer.springboot.rabbitmq.producer.config;

import io.summer.springboot.common.constant.MessageQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author summer
 */
@Configuration
public class MessageQueueConfiguration {

  @Bean
  public Queue topicMessageQueue() {
    return new Queue(MessageQueue.RABBIT_MQ_TOPIC_MESSAGE_QUEUE_NAME);
  }

  @Bean
  public Queue topicLogQueue() {
    return new Queue(MessageQueue.RABBIT_MQ_TOPIC_LOG_QUEUE_NAME);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(MessageQueue.RABBIT_MQ_TOPIC_EXCHANGE_NAME);
  }

  @Bean
  Binding bindingTopicMessageExchange(Queue topicMessageQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topicMessageQueue).to(topicExchange).with("topic.message.#");
  }

  @Bean
  Binding bindingTopicLogExchange(Queue topicLogQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(topicLogQueue).to(topicExchange).with("topic.#");
  }

//
//  @Bean
//  public Queue queue() {
//    return new Queue(MessageQueue.RABBIT_MQ_QUEUE_NAME);
//  }
//
//  @Bean
//  public Queue directQueue() {
//    return new Queue(MessageQueue.RABBIT_MQ_DIRECT_QUEUE_NAME);
//  }
//
//  @Bean
//  FanoutExchange fanoutExchange() {
//    return new FanoutExchange(MessageQueue.RABBIT_MQ_DIRECT_EXCHANGE_NAME);
//  }
//
//  @Bean
//  Binding bindingExchange(Queue queue, FanoutExchange fanoutExchange) {
//    return BindingBuilder.bind(queue).to(fanoutExchange);
//  }
//
//  @Bean
//  Binding bindingDirectExchange(Queue directQueue, FanoutExchange fanoutExchange) {
//    return BindingBuilder.bind(directQueue).to(fanoutExchange);
//  }

}
