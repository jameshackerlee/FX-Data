package com.calibre.fx.mapping;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client", catalog = "calibre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client implements Serializable {
  @Id
  @Column(name = "CLIENT_ID", unique = true, nullable = false, length = 20)
  private String client_id;

  @Column(name = "NAME", nullable = false, length = 50)
  private String name;

  @Column(name = "EMAIL", nullable = false, length = 100)
  private String email;

  // Using FetchType.EAGER Strategy (could not initialize proxy - no Session)
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "CLIENT_ID")
  private Set<ClientFxCode> clientFxCodes;

  @Builder
  public Client(String client_id, String name, String email) {
    this.client_id = client_id;
    this.name = name;
    this.email = email;
  }
}
