package com.calibre.fx.controller;

import com.calibre.fx.service.MailService;
import com.calibre.fx.mapping.Client;
import com.calibre.fx.mapping.ClientFxCode;
import com.calibre.fx.service.ClientService;
import com.calibre.fx.util.MyUtil;
import com.calibre.fx.util.WriteCsvToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/client")
public class ClientController {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public ClientService clientService;

  @Autowired
  public MailService mailService;

  @Autowired
  public WriteCsvToFile writeCsvToFile;

  @GetMapping
  public ResponseEntity<String> getApiFxData() {
    List<Client> clientList = clientService.findAll();

    for (Client client : clientList) {
      Set<ClientFxCode> clientFxCodes = client.getClientFxCodes();

      for (ClientFxCode clientFxCode : clientFxCodes) {
        System.out.println(client.getName() + ", " + client.getEmail() + ", " + clientFxCode.getCode());
      }
    }

    String strJson = "";
    return new ResponseEntity<String>(strJson, HttpStatus.OK);
  }

  /*
  @GetMapping("/export/download")
  public void exportToCsvDownload(HttpServletResponse response) throws IOException {
    String fileName = "obsval_" + new MyUtil().getTodayDateTime() + ".csv";
    String[] header = { "NO", "FOREX", "VALUE" };
    String[] data = { "no", "code", "value" };

    List<Client> clientList = clientService.findAll();
    List<ClientFxCode> clientFxCodeList = new ArrayList<>();
    Integer no = 1;

    for (Client client : clientList) {
      Set<ClientFxCode> clientFxCodes = client.getClientFxCodes();

      for (ClientFxCode clientFxCode : clientFxCodes) {
        clientFxCodeList.add(new ClientFxCode(no++, clientFxCode.getCode(), .60));
      }
    }

    for (ClientFxCode clientFxCode : clientFxCodeList) {
      logger.debug("List Info : " + clientFxCode.getNo() + ", " + clientFxCode.getCode() + ", " + clientFxCode.getValue());
    }

    writeCsvToFile.exportDownload(fileName, response, clientFxCodeList, header, data);
  }

  @GetMapping("/export/write")
  public void exportToCsvCreated() throws IOException {
    String fileName = "obsval_" + new MyUtil().getTodayDateTime() + ".csv";
    String[] header = { "NO", "FOREX", "VALUE" };
    String[] data = { "no", "code", "value" };

    List<Client> clientList = clientService.findAll();
    List<ClientFxCode> clientFxCodeList = new ArrayList<>();
    Integer no = 1;

    for (Client client : clientList) {
      Set<ClientFxCode> clientFxCodes = client.getClientFxCodes();

      for (ClientFxCode clientFxCode : clientFxCodes) {
        clientFxCodeList.add(new ClientFxCode(no++, clientFxCode.getCode(), .60));
      }
    }

    for (ClientFxCode clientFxCode : clientFxCodeList) {
      logger.debug("List Info : " + clientFxCode.getNo() + ", " + clientFxCode.getCode() + ", " + clientFxCode.getValue());
    }

    writeCsvToFile.clientFxCodeExportWrite(fileName, clientFxCodeList, header, data);
  }
  */

  @GetMapping("/email")
  public void email() {
    mailService.send("jameshackerlee@gmail.com", "test", "hello", "obsval_20210630_1117.csv");
  }
}
