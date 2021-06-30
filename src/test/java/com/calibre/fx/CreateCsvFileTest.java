package com.calibre.fx;

import com.calibre.fx.json.FxData;
import com.calibre.fx.mapping.Client;
import com.calibre.fx.mapping.ClientFxCode;
import com.calibre.fx.repository.ClientRepository;
import com.calibre.fx.service.ClientService;
import com.calibre.fx.util.MyUtil;
import com.calibre.fx.util.WriteCsvToFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreateCsvFileTest {
  @Autowired
  public ClientService clientService;

  @Autowired
  public WriteCsvToFile writeCsvToFile;

  @Test
  public void repositoryFindAllTest() throws IOException {
    String fileName = "obsval_" + new MyUtil().getTodayDateTime() + ".csv";

    List<Client> clientList = clientService.findAll();
    List<FxData> fxDataList = new ArrayList<>();
    Integer no = 1;

    for (Client client : clientList) {
      Set<ClientFxCode> clientFxCodes = client.getClientFxCodes();

      for (ClientFxCode clientFxCode : clientFxCodes) {
        fxDataList.add(new FxData(no++, clientFxCode.getCode(), .60));
      }
    }

    for (FxData fxData : fxDataList) {
      System.out.println("List Info : " + fxData.getNo() + ", " + fxData.getCode() + ", " + fxData.getValue());
    }

    writeCsvToFile.fxDataExportWrite(fileName, fxDataList);
  }
}
