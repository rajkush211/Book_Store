package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.CartDto;
import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("home/user/cart")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @PutMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<String>(iCartService.addToCart(cartDto), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<String>(iCartService.removeFromCart(cartDto), HttpStatus.OK);
    }
}
