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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class UserController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @GetMapping("/all-book")
    public ResponseEntity<Page<Book>> getAllBook(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity(iBookStoreService.getAllBook(pageable), HttpStatus.OK);
    }

    @GetMapping("/{author}")
    public ResponseEntity<Page<Book>> booksByAuthor(@PathVariable String author, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity(iBookStoreService.findByAuthor(author, pageable), HttpStatus.OK);
    }

    @GetMapping("/sort-asc/price")
    public ResponseEntity<Page<Book>> booksInAscendingOrderByPrice(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity(iBookStoreService.getAllBookByPriceAsc(pageable), HttpStatus.OK);
    }

    @GetMapping("/sort-desc/price")
    public ResponseEntity<Page<Book>> booksInDescendingOrderByPrice(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity(iBookStoreService.getAllBookByPriceDesc(pageable), HttpStatus.OK);
    }
}
