package com.calibre.fx.json;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
public class FxData {
  public String code;
  public String timestamp;
  public Integer gmtoffset;
  public Double open;
  public Double high;
  public Double low;
  public Double close;
  public Integer volume;
  public Double previousClose;
  public Double change;
  public Double change_p;

  public FxData() {
  }

  @Builder
  public FxData(Integer no, String code, Double value) {
    this.no = no;
    this.code = code;
    this.value = value;
  }

  // no needed for csv file
  public Integer no;
  public Double value;
}
