package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class UserController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @GetMapping("/all-book")
    public ResponseEntity<Book> getAllBook() {
        return new ResponseEntity(iBookStoreService.getAllBook(), HttpStatus.OK);
    }
}
