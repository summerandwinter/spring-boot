package com.summer.springboot.actuator.task;

import io.micrometer.core.annotation.Timed;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/4
 */
@Component
@Slf4j
@EnableScheduling
public class TaskDemo {
  private static final Random R = new Random();

  @Timed(value = "timer.demo",  extraTags = {"tag", "schedule"}, description = "schedule timer")
  @Scheduled(fixedDelay = 1000)
  public void testTimer() {
    log.info("timer started");
    int s = R.nextInt(10) + 2;
    try {
      TimeUnit.SECONDS.sleep(s);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("timer end");
  }
}
