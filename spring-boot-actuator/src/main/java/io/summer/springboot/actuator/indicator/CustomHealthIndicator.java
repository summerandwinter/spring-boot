package io.summer.springboot.actuator.indicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/28
 */
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    // custom health status
    builder.status("FATAL")
        .withDetail("name", "Custom health indicator")
        .withDetail("status", "Awesome");
  }
}
