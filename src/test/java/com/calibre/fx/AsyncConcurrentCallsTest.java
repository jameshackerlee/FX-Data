package com.calibre.fx;

import com.calibre.fx.json.FxData;
import com.calibre.fx.service.RestTemplateService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AsyncConcurrentCallsTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private RestTemplateService restTemplateService;

  @Value("${eod.api.token}")
  private String eodApiTokenId;


  @Test
  public void Test1() throws Exception {
    long start = System.currentTimeMillis();

    String url = "https://eodhistoricaldata.com/api/real-time/%s.FOREX?api_token=" + eodApiTokenId + "&fmt=json";

    CompletableFuture<FxData> task1 = restTemplateService.findFxData(url, "AUDUSD"); //"AUDUSD");
    CompletableFuture<FxData> task2 = restTemplateService.findFxData(url, "AUDUSD"); //"AUDUSD");
    CompletableFuture.allOf(task1, task2).join();

    logger.debug("result " + task1.get());
    assertThat(task1.get().getCode()).isNotEmpty();
  }

  @Test
  public void Test2() throws Exception {
    long start = System.currentTimeMillis();

    String url = "https://eodhistoricaldata.com/api/real-time/%s.FOREX?api_token=" + eodApiTokenId + "&fmt=json";

    List<CompletableFuture<FxData>> lists = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      lists.add(restTemplateService.findFxData(url, "AUDUSD"));
    }

    CompletableFuture.allOf(lists.toArray(new CompletableFuture[0])).join();

    for (int i = 0; i < 5; i++) {
      System.out.println("response: " + lists.get(i).get().toString());
    }

    logger.debug("Elapsed time : " + (System.currentTimeMillis() - start));
  }
}
