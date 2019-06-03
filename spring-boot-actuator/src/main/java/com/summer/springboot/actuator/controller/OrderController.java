package com.summer.springboot.actuator.controller;

import com.summer.springboot.actuator.entity.Order;
import com.summer.springboot.actuator.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */

@RestController
@RequestMapping("/order")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping(value = "createOrder")
  @ResponseBody
  public ResponseEntity<Boolean> createOrder(@RequestBody Order order) {
    return ResponseEntity.ok(orderService.createOrder(order));
  }
}
