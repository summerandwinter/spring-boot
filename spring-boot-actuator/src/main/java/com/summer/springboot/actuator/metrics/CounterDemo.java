package com.summer.springboot.actuator.metrics;

import com.summer.springboot.actuator.entity.Order;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/4
 */
public class CounterDemo {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  static {
    Metrics.addRegistry(new SimpleMeterRegistry());
  }

  public static void main(String[] args) throws Exception {
    Order order1 = new Order();
    order1.setOrderId("ORDER_ID_1");
    order1.setAmount(100);
    order1.setChannel("CHANNEL_A");
    order1.setCreateTime(LocalDateTime.now());
    createOrder(order1);
    Order order2 = new Order();
    order2.setOrderId("ORDER_ID_2");
    order2.setAmount(200);
    order2.setChannel("CHANNEL_B");
    order2.setCreateTime(LocalDateTime.now());
    createOrder(order2);
    Search.in(Metrics.globalRegistry).meters().forEach(each -> {
      StringBuilder builder = new StringBuilder();
      builder.append("name:")
          .append(each.getId().getName())
          .append(",tags:")
          .append(each.getId().getTags())
          .append(",type:").append(each.getId().getType())
          .append(",value:").append(each.measure());
      System.out.println(builder.toString());
    });
  }

  private static void createOrder(Order order) {
    //忽略订单入库等操作
    Metrics.counter("order.create",
        "channel", order.getChannel(),
        "createTime", FORMATTER.format(order.getCreateTime())).increment();
  }

}
