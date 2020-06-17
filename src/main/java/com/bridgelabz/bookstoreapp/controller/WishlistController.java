package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.WishlistDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.service.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/user/wishlist")
@CrossOrigin(value = "*")
public class WishlistController {

    @Autowired
    private IWishlistService iWishlistService;

//    @CacheEvict(value = "Book", key = "#cartDto.userId")
    @PutMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistDto wishlistDto) {
        return new ResponseEntity<String>(iWishlistService.addToWishlist(wishlistDto), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(@RequestBody WishlistDto wishlistDto) {
        return new ResponseEntity<String>(iWishlistService.removeFromWishlist(wishlistDto), HttpStatus.OK);
    }

//    @Cacheable(value = "Book", key = "#userId")
    @GetMapping("/getall/{userId}")
    public ResponseEntity<List<Book>> getAllBooksList(@PathVariable int userId) {
        return new ResponseEntity<List<Book>>(iWishlistService.getAllBooksList(userId), HttpStatus.OK);
    }
}
