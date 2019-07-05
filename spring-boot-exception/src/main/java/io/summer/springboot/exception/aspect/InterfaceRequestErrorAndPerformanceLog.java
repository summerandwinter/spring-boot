package io.summer.springboot.exception.aspect;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import io.summer.springboot.exception.common.ApiResponse;
import io.summer.springboot.exception.common.Constants;
import io.summer.springboot.exception.entity.AppTraceEntity;
import io.summer.springboot.exception.util.AppUtil;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 接口请求日志记录.
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@Component
@Aspect
@Slf4j
public class InterfaceRequestErrorAndPerformanceLog {

  private static final int PERFORM_BAD_VALUE = 3000;


  @Pointcut("execution(* com.summer.springbootexception.controller..*.*(..))")
  public void pointCut() {
  }

  /**
   * 处理接口请求日志.
   * @param pjp ProceedingJoinPoint
   * @return ApiResponse
   * @throws Throwable 抛出异常
   */
  @Around("pointCut()")
  public ApiResponse handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
    Stopwatch stopwatch = Stopwatch.createStarted();
    long startTime = System.currentTimeMillis();
    ApiResponse apiResponse;
    //执行访问接口操作
    apiResponse = (ApiResponse) pjp.proceed(pjp.getArgs());
    long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
    try {
      log.info(processLogInfo(startTime, consumeTime, apiResponse));
      // 此处将日志打印放入try-catch 是因为项目中有些对象实体bean过于复杂，导致序列化为json的时候报错，
      // 但是此处报错并不影响主要功能使用，只是返回结果日志没有打印，所以 catch 中也不做抛出异常处理
    } catch (Exception ex) {
      log.error(pjp.getSignature() + " 接口记录返回结果失败！，原因为：{}", ex.getMessage());
    }
    //当接口请求时间大于3秒时，标记为异常调用时间，并记录入库
    if (consumeTime > PERFORM_BAD_VALUE) {
      // TODO 放入消息队列
      log.info("超时");
    }
    return apiResponse;
  }

  private String processLogInfo(long startTime, long consumeTime, ApiResponse apiResponse) {
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    StringBuilder sb = new StringBuilder();
    if (sra != null) {
      HttpServletRequest request = sra.getRequest();
      AppTraceEntity traceEntity = AppUtil.getTraceEntity(request);
      String requestId = request.getHeader(Constants.NAME_OF_APP_TRACE_ID);
      if (requestId != null && !requestId.isEmpty()) {
        sb.append("[");
        sb.append(requestId);
        sb.append("]");
      }
      sb.append(" 请求地址：");
      sb.append(traceEntity.getUri());
      sb.append(" 执行耗时：");
      sb.append(consumeTime);

      String timestamp = request.getHeader(Constants.NAME_OF_REQUEST_TIME);
      if (timestamp != null && !timestamp.isEmpty()) {
        long requestTime = Long.parseLong(timestamp);
        long uptime = startTime - requestTime;
        sb.append(" 上行耗时：");
        sb.append(uptime);
      }
      sb.append(" 请求参数：");
      sb.append(JSONObject.toJSONString(traceEntity.getParams()));
      sb.append(" 返回值：");
      sb.append(JSONObject.toJSONString(apiResponse));
    }
    return sb.toString();
  }

}
