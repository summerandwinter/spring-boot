package io.summer.springboot.exception.config;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/27
 */
import io.summer.springboot.exception.interceptor.TraceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorRegister implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/demo/**");
  }
}
