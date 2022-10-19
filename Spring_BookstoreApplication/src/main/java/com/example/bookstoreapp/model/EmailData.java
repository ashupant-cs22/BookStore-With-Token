package com.example.bookstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailData {
    private String to;
    private String subject;
    private String body;
}

