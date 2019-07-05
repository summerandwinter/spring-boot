package io.summer.springboot.exception.exception;

import io.summer.springboot.exception.entity.AppTraceEntity;

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
