package com.calibre.fx.service;

import com.calibre.fx.json.FxData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class RestTemplateService {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private RestTemplate restTemplate;

  @Async
  public CompletableFuture<FxData> findFxData(String url, String code) throws InterruptedException {
    logger.debug("Starting service " + code);

    // ex, https://eodhistoricaldata.com/api/real-time/%s?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json
    logger.debug("URL : " + String.format(url, code));
    FxData fxData = restTemplate.getForObject(String.format(url, code), FxData.class);

    // My token id at Api access limited per daily and testing with public token id
    // FxData fxData = restTemplate.getForObject("https://eodhistoricaldata.com/api/real-time/EUR.FOREX?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json", FxData.class);

    Thread.sleep(100L);
    return CompletableFuture.completedFuture(fxData);
  }
}
