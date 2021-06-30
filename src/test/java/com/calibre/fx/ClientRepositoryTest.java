package com.calibre.fx;

import com.calibre.fx.mapping.Client;
import com.calibre.fx.repository.ClientRepository;
import com.calibre.fx.util.MyUtil;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {
  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private ClientRepository clientRepository;

  String name = "James Lee";
  String email = "jameshackerlee@gmail.com";

  @Test
  public void entityManagerQueryTest() {
    testEntityManager.persist(Client.builder().client_id(new MyUtil().makeClientIdGenerator())
        .name(name)
        .email(email)
        .build());

    List<Client> clientList = clientRepository.findByNameClientQuery(name);

    for (Client client : clientList) {
      assertThat(client.getName()).isEqualTo(name);
    }
  }

  @Test
  public void repositoryQueryTest() {
    clientRepository.save(Client.builder().client_id(new MyUtil().makeClientIdGenerator())
        .name(name)
        .email(email)
        .build());

    List<Client> clientList = clientRepository.findByNameClientQuery(name);

    clientList.stream().forEach(name -> System.out.println(name));
  }

  @Test
  public void repositoryFindAllTest() {
    List<Client> clientList = clientRepository.findAll();

    assertThat(clientList).size().isGreaterThan(0);
    clientList.stream().forEach(name -> System.out.println(name));
  }
}
