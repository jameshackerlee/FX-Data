package com.calibre.fx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailService {
  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String fromAddress;

  @Value("${csv.file.directory}")
  public String _FILE_DIRECTORY;

  public void send(String toEmailAddress, String subject, String body, String attachedFile) {
    try {
      logger.debug("from email " + fromAddress);

      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
      mimeMessageHelper.setFrom(fromAddress);
      mimeMessageHelper.setTo(toEmailAddress);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(body);

      FileSystemResource file = new FileSystemResource(new File(_FILE_DIRECTORY + attachedFile));
      mimeMessageHelper.addAttachment(attachedFile, file);

      javaMailSender.send(message);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void send(String toEmailAddress, String subject, String body) {
    try {
      logger.debug("from email " + fromAddress);

      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromAddress);
      message.setTo(toEmailAddress);
      message.setSubject(subject);
      message.setText(body);

      javaMailSender.send(message);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
