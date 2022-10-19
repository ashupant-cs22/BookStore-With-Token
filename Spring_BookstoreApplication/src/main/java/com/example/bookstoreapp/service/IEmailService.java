package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.model.EmailData;
import org.springframework.http.ResponseEntity;

public interface IEmailService {

    ResponseEntity<ResponseDTO> sendEmail(EmailData emailData);

    String getLink(String token);

    String getOrderLink(String token);
}

