package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{author}")
    public ResponseEntity<Book> booksByAuthor(@PathVariable String author) {
        return new ResponseEntity(iBookStoreService.findByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/sort-asc/price")
    public ResponseEntity<Book> booksInAscendingOrderByPrice() {
        return new ResponseEntity(iBookStoreService.getAllBookByPriceAsc(), HttpStatus.OK);
    }

    @GetMapping("/sort-desc/price")
    public ResponseEntity<Book> booksInDescendingOrderByPrice() {
        return new ResponseEntity(iBookStoreService.getAllBookByPriceDesc(), HttpStatus.OK);
    }
}
