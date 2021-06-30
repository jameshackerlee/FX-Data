package com.calibre.fx.schedule;

import com.calibre.fx.mapping.Client;
import com.calibre.fx.mapping.ClientFxCode;
import com.calibre.fx.service.ClientService;
import com.calibre.fx.service.RestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private TaskScheduler taskScheduler;

  @Autowired
  private CronConfig cronConfig;

  @Autowired
  private ClientFxDataTask clientFxDataTask;

  public void scheduleAllCrons() {
    cronConfig.getSchedules().forEach(cron -> taskScheduler.schedule(clientFxDataTask, new CronTrigger(cron)));
  }
}
