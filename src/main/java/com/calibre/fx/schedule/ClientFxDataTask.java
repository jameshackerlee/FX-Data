package com.calibre.fx.schedule;

import com.calibre.fx.mapping.Client;
import com.calibre.fx.mapping.ClientFxCode;
import com.calibre.fx.json.FxData;
import com.calibre.fx.service.ClientService;
import com.calibre.fx.service.MailService;
import com.calibre.fx.service.RestTemplateService;
import com.calibre.fx.util.MyUtil;
import com.calibre.fx.util.WriteCsvToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
@Scope("prototype")
public class ClientFxDataTask implements Runnable {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private RestTemplateService restTemplateService;

  @Autowired
  private ClientService clientService;

  @Autowired
  private MailService mailService;

  @Autowired
  private WriteCsvToFile writeCsvToFile;

  @Value("${eod.api.token}")
  private String eodApiTokenId;

  private String csvFileName;

  @Override
  public void run() {
    logger.debug("Eod Api TokenId " + eodApiTokenId);

    try {
      List<Client> clientList = clientService.findAll();

      // api with multi currency code
      getDataCallingApi(clientList);

    } catch (Exception e) {
    }
  }

  public void getDataCallingApi(List<Client> clientList) throws Exception {
    String url = "https://eodhistoricaldata.com/api/real-time/%s.FOREX?api_token=" + eodApiTokenId + "&fmt=json";
    int idx = 0;
    List<CompletableFuture<FxData>> fxDataCompletableLists;

    try {
      for (Client client : clientList) {
        Set<ClientFxCode> clientFxCodes = client.getClientFxCodes();
        fxDataCompletableLists = new ArrayList<>();

        for (ClientFxCode clientFxCode : clientFxCodes) {
          logger.debug("URL  : " + url);
          logger.debug("CODE : " + clientFxCode.getCode());
          logger.debug("Service : " + restTemplateService);

          fxDataCompletableLists.add(restTemplateService.findFxData(url, clientFxCode.getCode()));

          CompletableFuture.allOf(fxDataCompletableLists.toArray(new CompletableFuture[0])).join();

          // converted to FxData Object
          combineGroupFxDataListToCsvFile(fxDataCompletableLists);
        }

        try {
          // email to client
          StringBuffer body = new StringBuffer();
          body.append("Hello ").append(client.getName()).append(",\n\n");
          body.append("This is fx rate list for you").append("\n");
          body.append("Please see attached file.");
          body.append("\n\n");
          body.append("Thank you");

          mailService.send(client.getEmail(), "[This is Test email] The FX Rates of interest (" + new MyUtil().getTodayDateTime() + ")", body.toString(), this.csvFileName);

        } catch (Exception e) {
          // email error
          mailService.send(client.getEmail(), "[failure] An error occurred in processing (" + new MyUtil().getTodayDateTime() + ")", "Sorry, error ......");
        }
      }

    } catch (InterruptedException e1) {
      e1.printStackTrace();

    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

  public void combineGroupFxDataListToCsvFile(List<CompletableFuture<FxData>> fxDataCompletableLists) throws Exception {
    int length = fxDataCompletableLists.size();
    List<FxData> fxDataList = new ArrayList<>();

    for (int sop = 0; sop < length; sop++) {
      fxDataList.add(FxData.builder().no(sop + 1).code(fxDataCompletableLists.get(sop).get().getCode().replaceAll(".FOREX", ""))
      .value(fxDataCompletableLists.get(sop).get().getOpen()).build());
    }

    this.csvFileName = "obsval_" + new MyUtil().getTodayDateTime() + ".csv";

    // csv file created
    writeCsvToFile.fxDataExportWrite(csvFileName, fxDataList);
  }
}
