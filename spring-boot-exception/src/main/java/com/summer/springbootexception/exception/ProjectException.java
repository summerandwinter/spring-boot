package com.summer.springbootexception.exception;

import com.summer.springbootexception.entity.AppTraceEntity;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
public class ProjectException extends RuntimeException {

  private AppTraceEntity trace;

  public ProjectException(AppTraceEntity trace) {
    this.trace = trace;
  }

}
