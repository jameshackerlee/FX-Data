package com.calibre.fx;

import com.calibre.fx.schedule.CronConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetSchedulesMultiValueApplicationTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public CronConfig cronConfig;

  @Test
  public void schedulersValuesTest() {
    cronConfig.getSchedules().forEach(cron ->  System.out.println(cron));
  }
}
