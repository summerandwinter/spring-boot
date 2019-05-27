package com.summer.springbootexception.util;

import com.summer.springbootexception.entity.AppTraceEntity;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/24
 */
public class AppUtil {

  public static AppTraceEntity getTraceEntity(HttpServletRequest request) {
    Map<String, String[]> params = request.getParameterMap();
    Map<String, String> headers = getHeadersInfo(request);
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String localAddress = request.getLocalAddr();
    int localPort = request.getLocalPort();
    String remoteAddress = request.getRemoteAddr();
    AppTraceEntity traceEntity = new AppTraceEntity();
    traceEntity.setMethod(method);
    traceEntity.setUri(uri);
    traceEntity.setHeaders(headers);
    traceEntity.setParams(params);
    traceEntity.setLocalAddress(localAddress);
    traceEntity.setLocalPort(localPort);
    traceEntity.setRemoteAddress(remoteAddress);
    return traceEntity;
  }

  private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
    Map<String, String> map = new HashMap<>();
    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      map.put(key, value);
    }
    return map;
  }

}
