package com.calibre.fx;

import com.calibre.fx.service.MailService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmailSendingTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public MailService mailService;

  @Test
  public void sendingTest() {
    mailService.send("jameshackerlee@gmail.com", "test", "hello", "obsval_20210630_1117.csv");
  }
}
