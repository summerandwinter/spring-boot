package com.summer.springboot.exception.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * 特殊字符检测工具（防止传入非法字符和sql注入攻击）
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@Slf4j
public class IllegalStrFilterUtil {

  private static final String REG = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§ ";
  private static final String[] BLACK_LIST = {
      "DELETE", "ASCII", "UPDATE", "SELECT", "'",
      "SUBSTR(", "COUNT(", " OR ", " AND ", "DROP",
      "EXECUTE", "EXEC", "TRUNCATE", "INTO", "DECLARE",
      "MASTER"};
  /**
   * 对常见的sql注入攻击进行拦截
   *
   * @param sInput 传入需要检查的字符串
   * @return
   *  true 表示参数不存在SQL注入风险
   *  false 表示参数存在SQL注入风险
   */
  public static Boolean sqlStrFilter(String sInput) {
    if (sInput == null || sInput.trim().length() == 0) {
      return true;
    }
    sInput = sInput.toUpperCase();
    boolean flag = true;
    for (String text : BLACK_LIST) {
      if (sInput.contains(text)) {
        flag = false;
        break;
      }
    }
    return flag;
  }

  /**
   * 对非法字符进行检测
   *
   * @param sInput 需要检查的字符串
   * @return
   *  true 表示参数不包含非法字符
   *  false 表示参数包含非法字符
   */
  public static Boolean isIllegalStr(String sInput) {

    if (sInput == null || sInput.trim().length() == 0) {
      return true;
    }
    sInput = sInput.trim();
    Pattern compile = Pattern.compile(REG, Pattern.CASE_INSENSITIVE);
    Matcher matcher = compile.matcher(sInput);
    return matcher.find();
  }


}
