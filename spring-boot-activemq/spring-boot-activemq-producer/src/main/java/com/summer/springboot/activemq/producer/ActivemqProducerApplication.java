package com.summer.springboot.activemq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Activemq producer demo.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@SpringBootApplication
@EnableScheduling
@EnableJms
public class ActivemqProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ActivemqProducerApplication.class, args);
  }

}
