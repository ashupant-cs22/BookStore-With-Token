package com.example.bookstoreapp.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {

    public String firstName;

    public String lastName;

    public String email;

    public String password;

    public String address;
}

