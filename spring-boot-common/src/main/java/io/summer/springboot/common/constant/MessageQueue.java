package io.summer.springboot.common.constant;

/**
 * @author summer
 */
public class MessageQueue {

  public final static String RABBIT_MQ_QUEUE_NAME = "spring-boot-rabbitmq-queue";
  public final static String RABBIT_MQ_DIRECT_EXCHANGE_NAME = "message.direct";
  public final static String RABBIT_MQ_DIRECT_QUEUE_NAME = "message.direct.queue";
  public final static String RABBIT_MQ_TOPIC_EXCHANGE_NAME = "topic.message";
  public final static String RABBIT_MQ_TOPIC_MESSAGE_QUEUE_NAME = "topic.message.queue";
  public final static String RABBIT_MQ_TOPIC_LOG_QUEUE_NAME = "topic.log.queue";

}
