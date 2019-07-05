package io.summer.springboot.exception.handler;

import io.summer.springboot.exception.enums.ApiResponseStatus;
import io.summer.springboot.exception.common.ApiResponse;
import io.summer.springboot.exception.entity.AppTraceEntity;
import io.summer.springboot.exception.util.AppUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 控制器增强配合 ExceptionHandler 实现全局日志统一处理。
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

  private final ErrorAttributes errorAttributes;

  @Autowired
  public CommonExceptionHandler(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ApiResponse exceptionHandler(Exception e, HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
    AppTraceEntity traceEntity = AppUtil.getTraceEntity(request);
    // TODO 跟踪信息和错误堆栈信息放入消息队列做后续处理
    log.error("Exception: {}", traceEntity, e);
    return new ApiResponse<>(ApiResponseStatus.INTERNAL_SERVER_ERROR, body);
  }

  private boolean getTraceParameter(HttpServletRequest request) {
    String parameter = request.getParameter("trace");
    if (parameter == null) {
      return false;
    }
    return !"false".equals(parameter.toLowerCase());
  }

  private Map<String, Object> getErrorAttributes(HttpServletRequest request,
      boolean includeStackTrace) {
    return this.errorAttributes.getErrorAttributes(new ServletWebRequest(request) , includeStackTrace);
  }

}
