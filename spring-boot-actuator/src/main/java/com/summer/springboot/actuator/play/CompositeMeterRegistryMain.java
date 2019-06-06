package com.summer.springboot.actuator.play;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/6
 */
public class CompositeMeterRegistryMain {
  public static void main(String[] args) {
    CompositeMeterRegistry registry = new CompositeMeterRegistry();
    Counter counter = registry.counter("request.count");
    counter.increment();
    MeterRegistry simple1 = new SimpleMeterRegistry();
    MeterRegistry simple2 = new SimpleMeterRegistry();

    registry.add(simple1).add(simple2);
    counter.increment();
    System.out.println(counter.measure());
    StringBuilder builder = new StringBuilder();
    Search.in(simple1).meters().forEach(each -> {
      builder.append("name:")
          .append(each.getId().getName())
          .append(",tags:")
          .append(each.getId().getTags())
          .append(",type:").append(each.getId().getType())
          .append(",value:").append(each.measure()).append("\n");

    });
    Search.in(simple2).meters().forEach(each -> {
      builder.append("name:")
          .append(each.getId().getName())
          .append(",tags:")
          .append(each.getId().getTags())
          .append(",type:").append(each.getId().getType())
          .append(",value:").append(each.measure());
    });
    System.out.println(builder.toString());
  }

}
