package com.summer.springbootexception.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Carr on 2018/5/30 .
 */
class LocalIpAddressUtil {
  public static List<String> getLocalIP() {
    List<String> ipList = new ArrayList<String>();
    InetAddress ip = null;
    try {
      Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
      while (netInterfaces.hasMoreElements()) {
        NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
        // 遍历所有ip
        Enumeration<InetAddress> ips = ni.getInetAddresses();
        while (ips.hasMoreElements()) {
          ip = (InetAddress) ips.nextElement();
          if (null == ip || "".equals(ip)) {
            continue;
          }
          String sIP = ip.getHostAddress();
          if (sIP == null || sIP.indexOf(":") > -1) {
            continue;
          }
          ipList.add(sIP);
          System.out.println(sIP);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ipList;
  }

  /**
   * 获取本地ip地址，有可能会有多个地址, 若有多个网卡则会搜集多个网卡的ip地址
   */
  private static Set<InetAddress> resolveLocalAddresses() {
    Set<InetAddress> addrs = new HashSet<InetAddress>();
    Enumeration<NetworkInterface> ns = null;

    try {
      ns = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException e) {
      // ignored...
    }
    while (ns != null && ns.hasMoreElements()) {
      NetworkInterface n = ns.nextElement();
      Enumeration<InetAddress> is = n.getInetAddresses();
      while (is.hasMoreElements()) {
        InetAddress i = is.nextElement();
        System.out.println(i);
        if (!i.isLoopbackAddress() && !i.isLinkLocalAddress() && !i.isMulticastAddress()
            && !isSpecialIp(i.getHostAddress())) {
          addrs.add(i);
        }
      }
    }
    return addrs;
  }

  static String resolveLocalIps() {
    Set<InetAddress> addresses = resolveLocalAddresses();
    Set<String> set = new HashSet<String>();
    String ip = null;
    for (InetAddress address : addresses) {
      set.add(address.getHostAddress());
      ip = address.getHostAddress();
    }
    return ip;
  }

  private static boolean isSpecialIp(String ip) {
    if (":".contains(ip)) {
      return true;
    }
    if ("127.".contains(ip)) {
      return true;
    }
    if ("169.254.".contains(ip)) {
      return true;
    }
    if ("255.255.255.255".contains(ip)) {
      return true;
    }
    return false;
  }
}
