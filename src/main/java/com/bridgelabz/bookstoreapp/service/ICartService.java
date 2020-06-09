package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDto;

public interface ICartService {
    String addToCart(CartDto cartDto);

    String removeFromCart(CartDto cartDto);
}
