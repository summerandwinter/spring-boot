package com.summer.springbootexception.aspect;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.summer.springbootexception.common.ApiResponse;
import com.summer.springbootexception.exception.DataCenterException;
import com.summer.springbootexception.util.IllegalStrFilterUtil;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@Component
@Aspect
@Slf4j
public class InterfaceRequestErrorAndPerformanceLog {

  private static final int PERFORM_BAD_VALUE = 3000;


  @Pointcut("execution(* com.summer.springbootexception.controller..*.*(..))")
  public void pointCut() { }

  @Around("pointCut()")
  public ApiResponse handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
    Stopwatch stopwatch = Stopwatch.createStarted();
    ApiResponse apiResponse;
    log.info("执行Controller开始: " + pjp.getSignature() + " 参数：" + Lists.newArrayList(pjp.getArgs()).toString());
    //处理入参特殊字符和sql注入攻击
    checkRequestParam(pjp);
    //执行访问接口操作
    apiResponse = (ApiResponse) pjp.proceed(pjp.getArgs());
    long consumeTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
    log.info("耗时：" + consumeTime + "(毫秒).");
    try {
      log.info("执行Controller结束: " + pjp.getSignature() + "， 返回值：" + JSONObject.toJSONString(apiResponse));
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


  /**
   * 处理入参特殊字符和sql注入攻击
   * @author gmy
   * @date 15:37 2018/10/25
   */
  private void checkRequestParam(ProceedingJoinPoint pjp) {
    String str = String.valueOf(pjp.getArgs());
    if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
      String errContent = "访问接口：" + pjp.getSignature()
          + "，输入参数存在SQL注入风险！参数为：" + Lists.newArrayList(pjp.getArgs()).toString();
      log.info(errContent);
      throw new DataCenterException(errContent);
    }
    if (!IllegalStrFilterUtil.isIllegalStr(str)) {
      String errContent = "访问接口：" + pjp.getSignature()
          + ",输入参数含有非法字符!，参数为：" + Lists.newArrayList(pjp.getArgs()).toString();
      log.info(errContent);
      throw new DataCenterException(errContent);
    }
  }


}
