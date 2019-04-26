package com.summer.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/26
 */
@SpringBootApplication
@EnableJms
@EnableAsync
public class ActivemqConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ActivemqConsumerApplication.class, args);
  }

}
