package com.summer.springboot.actuator.indicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

/**
 * Another custom health indicator.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/28
 */
@Component
public class SelfHealthIndicator extends AbstractHealthIndicator {

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    builder.up()
        .withDetail("name", "Another custom health indicator width a different name")
        .withDetail("status", "Good");
  }
}
