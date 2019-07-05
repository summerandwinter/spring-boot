package io.summer.springboot.actuator.contributor;

import java.util.Collections;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Configuration;

/**
 * Custom InfoContributors.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/29
 */
@Configuration
public class CustomInfoContributor implements InfoContributor {

  @Override
  public void contribute(Builder builder) {
    builder.withDetail("example",
        Collections.singletonMap("key", "value"));
  }
}
