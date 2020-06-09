package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.repository.BookStoreRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

    @Autowired
    private ConverterService converterService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Override
    public String addToCart(CartDto cartDto) {
        Cart cart = converterService.convertToCartEntity(cartDto);
        if (cartRepository.existsCartByUserId(cart.getUserId()) && cartRepository.existsCartByBookId(cart.getBookId()))
            cartRepository.deleteCartByBookIdAndUserId(cart.getBookId(), cart.getUserId());
        cartRepository.save(cart);
        return "Added to cart successfully";
    }

    @Override
    public String removeFromCart(CartDto cartDto) {
        Cart cart = converterService.convertToCartEntity(cartDto);
        cartRepository.deleteCartByBookIdAndUserId(cart.getBookId(), cart.getUserId());
        return "Book Removed from cart Successfully";
    }

    @Override
    public List<Book> getAllCartBooks(int userId) {
        List<Book> cartBooks = new ArrayList<>();
        List<Cart> allByUserId = cartRepository.findAllByUserId(userId);
        for(Cart cart : allByUserId) {
            cartBooks.add(bookStoreRepository.findById(cart.getBookId()));
        }
        return cartBooks;
    }
}
