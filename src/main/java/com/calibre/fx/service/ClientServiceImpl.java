package com.calibre.fx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.calibre.fx.repository.ClientRepository;
import com.calibre.fx.mapping.Client;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("clientService")
public class ClientServiceImpl implements ClientService {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public List<Client> findAll() {
    List<Client> clientList = new ArrayList<>();
    clientRepository.findAll().forEach(e -> clientList.add(e));
//    clientList.stream().forEach(e -> System.out.println(e.getName() + " + " + e.getEmail()));

    return clientList;
  }

  @Override
  public Optional<Client> findById(String clientId) {
    return clientRepository.findById(clientId);
  }

  @Override
  public void save(Client client) {
    clientRepository.save(client);
  }

  @Override
  public void delete(String clientId) {
    clientRepository.deleteById(clientId);
  }
}
