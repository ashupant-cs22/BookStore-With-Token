package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.LoginDTO;
import com.example.bookstoreapp.dto.UserRegistrationDTO;
import com.example.bookstoreapp.model.UserRegistrationData;
import java.util.List;

public interface IUserRegistrationService {

    List<UserRegistrationData> getUserRegistrationData();

    UserRegistrationData getUserRegistrationDataByUserId(int tokenId);

    UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO);

    UserRegistrationData updateUserRegistrationData(int tokenId, UserRegistrationDTO userRegistrationDTO);

    UserRegistrationData getUserByEmailId(String email);

    UserRegistrationData userLogin(LoginDTO loginDTO);

    UserRegistrationData verifyUser(String token);
}
