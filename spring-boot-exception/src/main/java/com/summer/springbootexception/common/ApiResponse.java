package com.summer.springbootexception.common;

import com.summer.springbootexception.enums.ApiResponseStatus;
import java.io.Serializable;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@Data
public class ApiResponse<T> implements Serializable {

  private int code;
  private long timestamp;
  private String traceId;
  private String appTraceId;
  private String msg;
  private T data;

  public ApiResponse() {
    this(ApiResponseStatus.SUCCESS);
  }

  public ApiResponse(ApiResponseStatus status) {
    this.code = status.value();
    this.msg = status.getReasonPhrase();
    this.timestamp = System.currentTimeMillis();
    this.traceId = MDC.get(Constants.LOG_TRACE_ID);
    this.appTraceId = MDC.get(Constants.HTTP_HEADER_TRACE_ID);
  }

  public ApiResponse(@Nullable T body) {
    this(ApiResponseStatus.SUCCESS);
    this.data = body;
  }

  public ApiResponse(ApiResponseStatus status, @Nullable T body) {
    this(status);
    this.data = body;
  }

}
