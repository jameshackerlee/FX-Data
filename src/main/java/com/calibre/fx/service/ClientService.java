package com.calibre.fx.service;

import com.calibre.fx.mapping.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
  // get all client list
  List<Client> findAll();

  // get client by id
  Optional<Client> findById(String clientId);

  // save
  void save(Client client);

  // delete
  void delete(String clientId);
}
