package io.summer.springboot.exception.util;

import java.util.UUID;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/27
 */
public class TraceLogUtils {

  public static String getTraceId() {
    String uuid = UUID.randomUUID().toString();
    //去掉“-”符号
    return uuid.replaceAll("-", "");
  }

}
