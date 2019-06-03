package com.summer.springboot.actuator.entity;

import lombok.Data;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */
@Data
public class Message {
  private String orderId;
  private Long userId;
  private String content;
}
