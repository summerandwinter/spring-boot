package io.summer.springboot.common.vo;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

/**
 * @author summer
 */
@Data
public class MessageVO implements Serializable {

  private String id;
  private Long timestamp;
  private String message;

  public MessageVO() {
    this.timestamp = System.currentTimeMillis();
    this.id = UUID.randomUUID().toString();
  }

  public MessageVO(String message) {
    this.id = UUID.randomUUID().toString();
    this.message = message;
    this.timestamp = System.currentTimeMillis();
  }
}
