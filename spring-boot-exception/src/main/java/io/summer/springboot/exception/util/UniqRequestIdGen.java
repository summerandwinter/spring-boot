package io.summer.springboot.exception.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 生成 requestId
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/05/27
 */
public class UniqRequestIdGen {


  /**
  * 自增id，用于requestId的生成过程
   */
  private static AtomicLong lastId = new AtomicLong();



  public static String resolveReqId() {
    // 规则： hexIp(ip)base36(timestamp)-seq
    // 启动加载时的时间戳，用于requestId的生成过程
    long startTimeStamp = System.currentTimeMillis();
    String ip = LocalIpAddressUtil.resolveLocalIps();
    return hexIp(ip) + "-" + startTimeStamp + "-" + lastId.incrementAndGet();

  }

  /**
   * 将ip转换为定长8个字符的16进制表示形式：255.255.255.255 -> FAFFED
   * @param ip ip 地址
   * @return 格式化好的 ip 地址
   */
  private static String hexIp(String ip) {
    StringBuilder sb = new StringBuilder();
    if (ip != null) {
      for (String seg : ip.split("\\.")) {
        String h = Integer.toHexString(Integer.parseInt(seg));
        if (h.length() == 1) {
          sb.append("0");
        }
        sb.append(h);
      }
      return sb.toString();
    } else {
      return "0.";
    }

  }

}
