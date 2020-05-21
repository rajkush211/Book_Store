package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/admin")
public class AdminController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @GetMapping("/loadcsv")
    public String loadCSVData() {
        iBookStoreService.loadBookData();
        return "CSV file loaded successfully";
    }
}
