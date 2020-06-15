package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDto;
import com.bridgelabz.bookstoreapp.dto.CartQtyDto;
import com.bridgelabz.bookstoreapp.entity.Book;

import java.util.List;
import java.util.Map;

public interface ICartService {
    String addToCart(CartDto cartDto);

    String removeFromCart(CartDto cartDto);

    List<Book> getAllCartBooks(int userId);

    List<CartQtyDto> getBooks(int userId);
}
