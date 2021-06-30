package com.calibre.fx;

import com.calibre.fx.util.MyUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetValueApplicationTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${eod.api.token}")
  public String eodApiToken;

  @Value("${mail.smtp.sender}")
  public String mailSmtpSender;

  @Value("${mail.smtp.port}")
  public String mailSmtpPort;

  @Value("${mail.smtp.auth}")
  public String mailSmtpAuth;

  @Value("${mail.smtp.host}")
  public String mailSmtpHost;

  @Value("${mail.smtp.password}")
  public String mailSmtpPassword;

  @Test
  public void valuesTest() {
    System.out.println("get value : " + eodApiToken);
    System.out.println();
    System.out.println("mail info");
    System.out.println(mailSmtpSender);
    assertThat(mailSmtpSender).isEqualTo("jameshackerlee@gmail.com");

    System.out.println(mailSmtpPort);
    System.out.println(mailSmtpAuth);
    System.out.println(mailSmtpHost);
    System.out.println(mailSmtpPassword);
  }
}
