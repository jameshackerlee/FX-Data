package com.calibre.fx.mapping;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client_fx_code", catalog = "calibre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientFxCode implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "FX_CODE_ID", unique = true, nullable = false)
  public Integer fx_code_id;

  @Column(name = "client_id", nullable = false, length = 20)
  public String client_id;

  @Column(name = "CODE", nullable = false, length = 20)
  public String code;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID", insertable = false, updatable = false)
  private Client client;

  @Builder
  public ClientFxCode(Integer fx_code_id, String client_id, String code) {
    this.fx_code_id = fx_code_id;
    this.client_id = client_id;
    this.code = code;
  }
}