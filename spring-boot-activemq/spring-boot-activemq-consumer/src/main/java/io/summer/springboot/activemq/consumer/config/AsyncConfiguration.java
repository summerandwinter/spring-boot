package io.summer.springboot.activemq.consumer.config;

import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configuration for async task execution pool.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/4/27
 */
@Configuration
public class AsyncConfiguration {

  @Value("${spring.task.execution.pool.allow-core-thread-timeout}")
  private boolean allowCoreThreadTimeout;
  @Value("${spring.task.execution.pool.core-size}")
  private int coreSize;
  @Value("${spring.task.execution.pool.max-size}")
  private int maxSize;
  @Value("${spring.task.execution.pool.queue-capacity}")
  private int queueCapacity;
  @Value("${spring.task.execution.thread-name-prefix}")
  private String threadNamePrefix;

  /**
   * Create async task executor bean.
   * @return ThreadPoolTaskExecutor
   */
  @Bean
  public ThreadPoolTaskExecutor asyncTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeout);
    threadPoolTaskExecutor.setCorePoolSize(coreSize);
    threadPoolTaskExecutor.setMaxPoolSize(maxSize);
    threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
    threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
    threadPoolTaskExecutor.setRejectedExecutionHandler(new CallerRunsPolicy());
    threadPoolTaskExecutor.initialize();
    return  threadPoolTaskExecutor;
  }

}
