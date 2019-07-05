package com.summer.springboot.exception.entity;

import java.util.Map;
import lombok.Data;

/**
 * 全局跟踪信息实体类
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/24
 */
@Data
public class AppTraceEntity {

  private int localPort;
  private String localAddress;
  private String remoteAddress;
  private String method;
  private String uri;
  private Map<String, String> headers;
  private Map<String, String[]> params;
}
