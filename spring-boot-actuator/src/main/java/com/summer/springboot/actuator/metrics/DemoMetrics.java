package com.summer.springboot.actuator.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/2
 */
public class DemoMetrics  implements MeterBinder {
  private AtomicInteger count = new AtomicInteger(0);
  @Override
  public void bindTo(MeterRegistry meterRegistry) {
    Gauge.builder("demo.gauge", count, AtomicInteger::get)
        .tags("host", "localhost")
        .description("demo of custom metrics")
        .baseUnit("beans")
        .register(meterRegistry);
    Gauge.builder("demo.gauge", count, AtomicInteger::get)
        .tags("host", "localhost1")
        .description("demo of custom metrics")
        .baseUnit("beans")
        .register(meterRegistry);
    Counter counter = Counter.builder("demo.counter")
        .baseUnit("beans")
        .description("a description of what this counter does")
        .tags("region", "test")
        .register(meterRegistry);
    counter.increment();
  }
}
