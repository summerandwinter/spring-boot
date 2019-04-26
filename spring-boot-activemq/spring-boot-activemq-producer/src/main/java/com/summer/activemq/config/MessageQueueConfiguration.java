package com.summer.activemq.config;

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
  public Queue queue(){
    return new ActiveMQQueue("summer.queue");
  }
  @Bean
  public Topic topic(){
    return new ActiveMQTopic("summer.topic");
  }
  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
  }
  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setPubSubDomain(true);
    bean.setConnectionFactory(connectionFactory);
    return bean;
  }
  @Bean
  public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
    bean.setConnectionFactory(connectionFactory);
    return bean;
  }
  @Bean
  public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory){
    return new JmsMessagingTemplate(connectionFactory);
  }

}
