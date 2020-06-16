package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.payload.request.LoginRequest;
import com.bridgelabz.bookstoreapp.payload.request.SignupRequest;
import com.bridgelabz.bookstoreapp.service.AuthenticateUserServiceImpl;
import com.bridgelabz.bookstoreapp.service.IAuthenticateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private IAuthenticateUserService iAuthenticateUserService;

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

       return iAuthenticateUserService.logInUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return iAuthenticateUserService.registerUser(signUpRequest);
    }
}