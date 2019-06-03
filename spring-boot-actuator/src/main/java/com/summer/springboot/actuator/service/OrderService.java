package com.summer.springboot.actuator.service;

import com.summer.springboot.actuator.entity.Message;
import com.summer.springboot.actuator.entity.Order;
import io.micrometer.core.instrument.Metrics;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */
@Slf4j
@Service
public class OrderService {
  private static final Random R = new Random();

  @Autowired
  private MessageService messageService;

  public Boolean createOrder(Order order) {
    //模拟下单
    try {
      int ms = R.nextInt(50) + 50;
      TimeUnit.MILLISECONDS.sleep(ms);
      log.info("保存订单模拟耗时{}毫秒...", ms);
    } catch (Exception e) {
      //no-op
    }
    //记录下单总数
    Metrics.counter("order.count").increment();
    //发送消息
    Message message = new Message();
    message.setContent("模拟短信...");
    message.setOrderId(order.getOrderId());
    message.setUserId(order.getUserId());
    messageService.sendMessage(message);
    return true;
  }
}
