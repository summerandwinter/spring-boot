package com.summer.springboot.actuator.service;

import com.summer.springboot.actuator.entity.Message;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */
@Slf4j
@Service
public class MessageService implements InitializingBean {
  private static final BlockingQueue<Message> QUEUE = new ArrayBlockingQueue<>(500);
  private static BlockingQueue<Message> realQueue;
  private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
  private static final Random R = new Random();

  static {
    realQueue = Metrics
        .gauge("message.gauge", Tags.of("message.gauge", "message.queue.size"), QUEUE, Collection::size);
  }

  public void sendMessage(Message message) {
    try {
      realQueue.put(message);
    } catch (InterruptedException e) {
      //no-op
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    EXECUTOR.execute(() -> {
      while (true) {
        try {
          Message message = realQueue.take();
          log.info("模拟发送短信,orderId:{},userId:{},内容:{},耗时:{}毫秒", message.getOrderId(), message.getUserId(),
              message.getContent(), R.nextInt(50));
        } catch (Exception e) {
          throw new IllegalStateException(e);
        }
      }
    });
  }
}
