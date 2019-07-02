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
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */

@RestController
@RequestMapping("/demo")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @RequestMapping("/redirect")
  public RedirectView redirect() {
    RedirectView redirectTarget = new RedirectView();
    redirectTarget.setUrl("http://192.168.0.170:18004/demo/anotherRedirect");
    return redirectTarget;
  }

  @RequestMapping("/anotherRedirect")
  public RedirectView anotherRedirect() {
    RedirectView redirectTarget = new RedirectView();
    redirectTarget.setUrl("http://www.baidu.com?a=b");
    return redirectTarget;
  }

  @PostMapping(value = "createOrder")
  @ResponseBody
  public ResponseEntity<Boolean> createOrder(@RequestBody Order order) {
    return ResponseEntity.ok(orderService.createOrder(order));
  }
}
