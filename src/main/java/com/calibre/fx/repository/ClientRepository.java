package com.calibre.fx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calibre.fx.mapping.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/* JpaRepository already implement
 * save(), findOne(), findAll(), count(), delete() method
 */

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
  @Query("select client from Client client where UPPER(client.name) = UPPER(:name) ")
  List<Client> findByNameClientQuery(@Param("name") String name);

  // if native query, implementing here
  /*
  Java 12 version, Raw String Literals
  String myString =
        `select client.client_id as client_id
           from client client
          where 1 = 1`;
  */
  @Query(value =
      "select client.client_id as client_id," +
      "       client.name as name," +
      "       client.email as email," +
      "       fx.fx_code_id as fx_code_id," +
      "       fx.code as email," +
      "       (" +
      "       select count(*) from client" +
      "       ) as count," +
      "       length(client.name) as name_length" +
      "  from CLIENT client" +
      " inner join CLIENT_FX_CODE fx" +
      "    on client.client_id = fx.client_id" +
      " where 1 = 1",
      nativeQuery = true)
  Client nativeQuery();
}