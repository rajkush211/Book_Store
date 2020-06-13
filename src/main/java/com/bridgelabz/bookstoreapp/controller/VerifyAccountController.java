package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verifyaccount")
@CrossOrigin(value = "*")
public class VerifyAccountController {

    @Autowired
    private IBookStoreService iBookStoreService;

    @GetMapping("/{userId}")
    public ResponseEntity<String> verifyAccount(@PathVariable long userId) {
        return new ResponseEntity(iBookStoreService.verifyUserAccount(userId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Book> getAllBook() {
        return new ResponseEntity(iBookStoreService.getAll(), HttpStatus.OK);
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
