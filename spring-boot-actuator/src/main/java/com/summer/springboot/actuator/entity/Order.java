package com.summer.springboot.actuator.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */
@Data
public class Order {
  private String orderId;
  private Long userId;
  private Integer amount;
  private LocalDateTime createTime;
}
