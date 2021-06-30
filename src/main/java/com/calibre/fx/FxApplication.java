package com.calibre.fx;

/*
 * multi cron job scheduler
 * Referendces :
 * https://stackoverflow.com/questions/40929161/spring-boot-one-scheduled-task-using-multiple-cron-expressions-from-yaml-file
 */

import com.calibre.fx.schedule.ScheduledTasks;
import com.sun.javafx.robot.impl.FXRobotHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableScheduling
@SpringBootApplication
@EnableAsync
public class FxApplication {
  public static Logger logger = LoggerFactory.getLogger(FxApplication.class);

  @Bean
  public TaskScheduler taskScheduler() {
    return new ConcurrentTaskScheduler();
  }

  public static void main(String[] args) throws Exception {
    logger.trace("test trace");
    logger.debug("test debug");

    ApplicationContext ctx = SpringApplication.run(FxApplication.class);
    ScheduledTasks scheduledTasks = ctx.getBean(ScheduledTasks.class);
    scheduledTasks.scheduleAllCrons();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("FxData-");
    executor.initialize();
    return executor;
  }
}
