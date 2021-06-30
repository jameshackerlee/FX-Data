package com.calibre.fx;

import com.calibre.fx.json.FxData;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApiTemplateTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());
  final String uri = "https://eodhistoricaldata.com/api/real-time/EUR.FOREX?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";

  @Test
  public void getAPIStringTest() {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(uri, String.class);

    System.out.println("Result : " + result);
  }

  @Test
  public void getAPITest2() {
    RestTemplate restTemplate = new RestTemplate();
    FxData fxData = restTemplate.getForObject(uri, FxData.class);

    System.out.println("Result open : " + fxData.getOpen());
    System.out.println("Result code : " + fxData.getCode());
  }

  @Test
  public void getAPITest3() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<FxData> fxData = restTemplate.getForEntity(uri, FxData.class);

    System.out.println("Result : " + fxData);
  }

  @Test
  public void getAPITest4() {
    String uri = "https://eodhistoricaldata.com/api/real-time/{code}?api_token={token}&fmt=json";
    String code = "EUR.FOREX";
    String token = "OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX";

    Map<String, String> params = new HashMap<String, String>();
    params.put("code", code);
    params.put("token", token);

    RestTemplate restTemplate = new RestTemplate();
    FxData fxData = restTemplate.getForObject(uri, FxData.class, params);

    System.out.println("Result : " + fxData.getCode());
    System.out.println("Result : " + fxData.getOpen());
    assertThat(fxData.getCode()).isEqualTo(code);
  }
}
