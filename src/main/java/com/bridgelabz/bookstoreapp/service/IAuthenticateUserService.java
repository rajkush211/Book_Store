package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.payload.request.LoginRequest;
import com.bridgelabz.bookstoreapp.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthenticateUserService {

    ResponseEntity logInUser(LoginRequest loginRequest);
    ResponseEntity registerUser(SignupRequest signUpRequest);
}
