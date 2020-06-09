package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.WishlistDto;
import com.bridgelabz.bookstoreapp.service.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/user/wishlist")
public class WishlistController {

    @Autowired
    private IWishlistService iWishlistService;

    @PutMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestBody WishlistDto wishlistDto) {
        return new ResponseEntity<String>(iWishlistService.addToWishlist(wishlistDto), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(@RequestBody WishlistDto wishlistDto) {
        return new ResponseEntity<String>(iWishlistService.removeFromWishlist(wishlistDto), HttpStatus.OK);
    }
}
