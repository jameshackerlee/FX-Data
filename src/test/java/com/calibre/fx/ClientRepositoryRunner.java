package com.calibre.fx;

import com.calibre.fx.mapping.Client;
import com.calibre.fx.service.ClientService;
import com.calibre.fx.util.MyUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ClientRepositoryRunner {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ClientService clientService;

  @Test
  public void runTest() throws Exception {
    String clientId = new MyUtil().makeClientIdGenerator();
    clientService.save(Client.builder()
                       .client_id(clientId)
                       .name("John Lee")
                       .email("johnny@gmail.com")
                       .build());

    clientService.findById(clientId).ifPresent(System.out::println);

    Optional<Client> clientOptional = clientService.findById(clientId);

    if (clientOptional.isPresent()) {
      logger.debug(clientOptional.get().getName());
    } else {
      logger.debug("No client found with id %s\n", clientId);
    }

    // get list of clients
    List<Client> clientList = clientService.findAll();
    clientList.forEach(client -> System.out.println(client.getName()));

    // delete client by clientId
    clientService.delete(clientId);
  }

}
