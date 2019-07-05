package com.summer.springboot.exception.controller;

import com.summer.springboot.exception.enums.ApiResponseStatus;
import com.summer.springboot.exception.common.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/5/23
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

  @RequestMapping("")
  @ResponseBody
  public Map<String, Object> index() {
    Map<String, Object> data = new HashMap<>(2);
    data.put("code", 0);
    data.put("msg", "This is a demo!");
    return data;
  }



  @RequestMapping("500")
  @ResponseBody
  public ApiResponse five() {
    int a = 10 / 0;
    return new ApiResponse<>();
  }

  @RequestMapping("error")
  @ResponseBody
  public ApiResponse error() {
    Map<String, Object> extra = new HashMap<>(2);
    extra.put("timestamp", System.currentTimeMillis());
    extra.put("device", "chrome");
    return new ApiResponse<>(ApiResponseStatus.SESSION_EXPIRED, extra);
  }

  @RequestMapping("fail")
  @ResponseBody
  public ApiResponse fail() {
    return new ApiResponse(ApiResponseStatus.USER_NOT_EXISTS);
  }

  @RequestMapping("success")
  @ResponseBody
  public ApiResponse success() {
    return new ApiResponse();
  }

  @RequestMapping("data")
  @ResponseBody
  public ApiResponse data(String id) {
    Map<String, Object> data = new HashMap<>(2);
    data.put("id", id);
    data.put("name", "summer");
    return new ApiResponse<>(data);
  }

  @PostMapping("post")
  @ResponseBody
  public ApiResponse post() {
    Map<String, Object> data = new HashMap<>(2);
    data.put("extra", "This is a post method");
    return new ApiResponse<>(data);
  }

}
