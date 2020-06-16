package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.CartDto;
import com.bridgelabz.bookstoreapp.dto.CartQtyDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home/user/cart")
@CrossOrigin(value = "*")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @CacheEvict(value = "Book", key = "#cartDto.userId")
    @PutMapping("/add-update")
    public String addToCart(@RequestBody CartDto cartDto) {
        System.out.println("Updating records");
        return iCartService.addToCart(cartDto);
    }

    @CacheEvict(value = "Book", key = "#cartDto.userId")
    @PutMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<String>(iCartService.removeFromCart(cartDto), HttpStatus.OK);
    }

    @Cacheable(value = "Book", key = "#userId")
    @GetMapping("/getall/{userId}")
    public List<CartQtyDto> getall(@PathVariable int userId) {
        System.out.println("getting books from cart");
        return iCartService.getBooks(userId);
    }
}
