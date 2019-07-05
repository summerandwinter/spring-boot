package io.summer.springboot.exception.controller;

import io.summer.springboot.exception.enums.ApiResponseStatus;
import io.summer.springboot.exception.common.ApiResponse;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 自定义错误页面处理
 * 继承 ErrorController 重写 getErrorPath() 方法， 将错误页面定义为 ERROR_PATH 路径
 * 在 ERROR_PATH 中实现具体的处理
 * 它有能力处理 404 和其他错误信息， 其他错误信息放在 @see CommonExceptionHandler 中处理
 * 这里只处理 @see CommonExceptionHandler 捕获不到的 404 错误
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@Controller
public class CustomErrorController implements ErrorController {

  private final ErrorAttributes errorAttributes;
  private static final String ERROR_PATH = "/error";

  @Autowired
  public CustomErrorController(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @RequestMapping(value = ERROR_PATH)
  @ResponseBody
  public ApiResponse error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
    return new ApiResponse<>(ApiResponseStatus.PAGE_NOT_FOUND, body);

  }

  private boolean getTraceParameter(HttpServletRequest request) {
    String parameter = request.getParameter("trace");
    if (parameter == null) {
      return false;
    }
    return !"false".equals(parameter.toLowerCase());
  }

  private Map<String, Object> getErrorAttributes(HttpServletRequest request,
      boolean includeStackTrace) {
    return this.errorAttributes.getErrorAttributes(new ServletWebRequest(request) , includeStackTrace);
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode != null) {
      try {
        return HttpStatus.valueOf(statusCode);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
