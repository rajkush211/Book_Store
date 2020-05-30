package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verifyaccount")
public class VerifyAccountController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @GetMapping("/{userId}")
    public ResponseEntity<String> verifyAccount(@PathVariable long userId) {
        return new ResponseEntity(iBookStoreService.verifyUserAccount(userId), HttpStatus.OK);
    }
}
