package com.summer.springboot.activemq.producer.config;

import javax.jms.Queue;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

/**
 * Configuration for activemq.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@Configuration
public class MessageQueueConfiguration {

  @Value("${spring.activemq.user}")
  private String userName;
  @Value("${spring.activemq.password}")
  private String password;
  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("summer.queue");
  }

  @Bean
  public Topic topic() {
    return new ActiveMQTopic("summer.topic");
  }

  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
  }

  /**
   * Configuration for topic.
   * @param connectionFactory ActiveMQConnectionFactory
   * @return JmsListenerContainerFactory
   */
  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerTopic(
      ActiveMQConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setPubSubDomain(true);
    bean.setConnectionFactory(connectionFactory);
    return bean;
  }

  /**
   * Configuration for queue.
   * @param connectionFactory ActiveMQConnectionFactory
   * @return JmsListenerContainerFactory
   */
  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerQueue(
      ActiveMQConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setConnectionFactory(connectionFactory);
    return bean;
  }

  @Bean
  public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory) {
    return new JmsMessagingTemplate(connectionFactory);
  }

}
