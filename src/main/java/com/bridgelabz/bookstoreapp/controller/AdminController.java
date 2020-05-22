package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/admin")
public class AdminController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @GetMapping("/loadcsv")
    public String loadCSVData() {
        iBookStoreService.loadBookData();
        return "CSV loaded successfully";
    }

    @PostMapping("/addbook")
    public ResponseEntity addNewBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity(iBookStoreService.addNewBook(bookDto), HttpStatus.CREATED);
    }
}
