package com.calibre.fx;

import com.calibre.fx.mapping.Client;
import com.calibre.fx.mapping.ClientFxCode;
import com.calibre.fx.repository.ClientRepository;
import com.calibre.fx.util.MyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryFindAllTest {
  @Autowired
  private ClientRepository clientRepository;

  @Test
  public void repositoryFindAllTest() {
    List<Client> clientList = clientRepository.findAll();

    clientList.stream().forEach(name -> System.out.println("name : " + name.getName()));

    for (Client client : clientList) {
      Set<ClientFxCode> clientFxCodes = client.getClientFxCodes();

      for (ClientFxCode clientFxCode : clientFxCodes) {
        System.out.println(client.getName() + ", " + clientFxCode.getCode());
      }
    }
  }
}
