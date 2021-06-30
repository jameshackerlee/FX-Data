package com.calibre.fx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyUtil {
  public static Logger logger = LoggerFactory.getLogger(MyUtil.class);

  public static boolean isNumber(String text) {
    try {
      Integer.parseInt(text);
      return true;

    } catch (NumberFormatException e) {
      return false;
    }
  }

  public String makeClientIdGenerator() {
    StringBuilder strKey = new StringBuilder();

    try {
      // Today
      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
      Date fdate = new Date(new Date().getTime());
      String sfdt = formatter.format(fdate);

      // Alphabet
      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      strKey.append(sfdt.substring(4, 6));

      Character character;
      for (int i = 0; i < 16; i++) {
        character = alphabet.charAt((int) Math.floor(Math.random() * alphabet.length()));
        strKey.append(character);
      }

      strKey.append(sfdt.substring(6, 8));

    } catch (Exception e) {
      logger.debug("makeClientIdGenerator Error : " + e.getMessage());
    }

    logger.debug("CLIENT ID " + strKey);
    return strKey.toString();
  }

  public String getTodayDateTime() {
    // Today
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd_HH");
    Date fdate = new Date(new Date().getTime());
    return formatter.format(fdate) + "00";
  }
}
