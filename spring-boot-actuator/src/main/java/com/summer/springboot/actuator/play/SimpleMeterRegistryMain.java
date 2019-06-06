package com.summer.springboot.actuator.play;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/6
 */
public class SimpleMeterRegistryMain {
  public static void main(String[] args) {
    SimpleMeterRegistry simple = new SimpleMeterRegistry();
    Counter counter = simple.counter("request.count");
    counter.increment();
    System.out.println(counter.measure());
    StringBuilder builder = new StringBuilder();
    Search.in(simple).meters().forEach(each -> {
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
