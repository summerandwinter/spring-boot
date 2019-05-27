package com.summer.springbootexception.common;

import com.summer.springbootexception.enums.ApiResponseStatus;
import java.io.Serializable;
import org.springframework.lang.Nullable;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
public class ApiResponse<T> implements Serializable {

  private int code;
  private String msg;
  private T data;

  public ApiResponse() {
    this(ApiResponseStatus.SUCCESS);
  }

  public ApiResponse(ApiResponseStatus status) {
    this.code = status.value();
    this.msg = status.getReasonPhrase();
  }

  public ApiResponse(@Nullable T body) {
    this(ApiResponseStatus.SUCCESS);
    this.data = body;
  }

  public ApiResponse(ApiResponseStatus status, @Nullable T body) {
    this(status);
    this.data = body;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
