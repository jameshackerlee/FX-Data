package com.calibre.fx;

import com.calibre.fx.mapping.Client;
import com.calibre.fx.util.MyUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class LombokTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void clientTest() {
    String clientId = new MyUtil().makeClientIdGenerator();
    String name = "James Lee";
    String email = "jameshackerlee@gmail.com";

    Client client = Client.builder()
        .client_id(clientId)
        .name(name)
        .email(email)
        .build();

    logger.debug("Client Id " + clientId);
    assertThat(client.getName()).isEqualTo(name);
    assertThat(client.getEmail()).isEqualTo(email);

    assertThat(client.getName()).isEqualTo("John");
  }
}
