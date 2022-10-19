package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.model.UserRegistrationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistrationData, Integer> {

    UserRegistrationData findUserRegistrationDataByEmail(String email);

    UserRegistrationData findByEmailAndPassword(String email, String password);
}

