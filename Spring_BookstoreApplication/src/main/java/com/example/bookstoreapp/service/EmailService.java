package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.EmailData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService implements IEmailService{

    @Override
    public ResponseEntity<ResponseDTO> sendEmail(EmailData emailData) {
        final String fromEmail = "ashupant.cs22@gmail.com";
        final String fromPassword = "Yet to be done";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        };

        Session session = Session.getInstance(properties, authenticator);
        MimeMessage mail = new MimeMessage(session);
        try {
            mail.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mail.addHeader("format", "flowed");
            mail.addHeader("Content-Transfer-Encoding", "8bit");
            mail.setFrom(new InternetAddress(fromEmail));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailData.getTo()));
            mail.setText(emailData.getBody(), "UTF-8");
            mail.setSubject(emailData.getSubject(), "UTF-8");

            Transport.send(mail);
            ResponseDTO responseDTO = new ResponseDTO("Sent email ", mail,null);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        } catch ( MessagingException e) {
            e.printStackTrace();
        }

        ResponseDTO responseDTO = new ResponseDTO("ERROR : Couldn't send email", null,null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String getLink(String token) {
        return "http://localhost:8080/user_registration/verify/" + token;
    }

    @Override
    public String getOrderLink(String token) {
        return "http://localhost:8080/order/verify/" + token;
    }
}

