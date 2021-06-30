package com.calibre.fx;

import com.calibre.fx.util.MyUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyUtilTest {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void getExcelFileNameTest() {
    logger.debug("obsval_" + new MyUtil().getTodayDateTime());
  }
}