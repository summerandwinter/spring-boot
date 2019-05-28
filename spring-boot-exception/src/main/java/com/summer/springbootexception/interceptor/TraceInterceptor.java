package com.summer.springbootexception.interceptor;

import com.summer.springbootexception.common.Constants;
import com.summer.springbootexception.util.TraceLogUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/27
 */
@Component
public class TraceInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    // 日志跟踪 id
    MDC.put(Constants.LOG_TRACE_ID, TraceLogUtils.getTraceId());
    String appTraceId = request.getHeader(Constants.NAME_OF_HTTP_HEADER_TRACE_ID);
    // 请求头跟踪 id
    if (appTraceId != null && !appTraceId.isEmpty()) {
      MDC.put(Constants.HTTP_HEADER_TRACE_ID, appTraceId);
    }

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
    MDC.clear();
  }

}
