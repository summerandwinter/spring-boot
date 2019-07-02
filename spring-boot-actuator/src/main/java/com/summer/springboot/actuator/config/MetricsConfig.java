package com.summer.springboot.actuator.config;

import com.summer.springboot.actuator.metrics.DemoMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/2
 */
@Configuration
public class MetricsConfig {

  @Bean
  public DemoMetrics demoMetrics() {
    return new DemoMetrics();
  }

//  @Bean
//  public ActivemqConsumerMetrics activemqConsumerMetrics() {
//    return  new ActivemqConsumerMetrics();
//  }
}
