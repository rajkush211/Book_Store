package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDto;
import com.bridgelabz.bookstoreapp.dto.CartQtyDto;
import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.repository.BookStoreRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import com.bridgelabz.bookstoreapp.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@PropertySource("classpath:message.properties")
public class CartServiceImpl implements ICartService {

    @Autowired
    private ConverterService converterService;

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private Environment environment;

    @Override
    public String addToCart(CartDto cartDto, String token) {
        if (jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Cart cart = converterService.convertToCartEntity(cartDto);
            cart.setUsername(username);
            if (cartRepository.existsCartByUsername(username) && cartRepository.existsCartByBookId(cart.getBookId()))
                cartRepository.deleteCartByBookIdAndUsername(cart.getBookId(), cart.getUsername());
            cartRepository.save(cart);
            return environment.getProperty("ADDED_TO_CART");
        }
        return environment.getProperty("JWT_NOT_VALID");
    }

    @Override
    public String removeFromCart(CartDto cartDto, String token) {
        if (jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Cart cart = converterService.convertToCartEntity(cartDto);
            cart.setUsername(username);
            cartRepository.deleteCartByBookIdAndUsername(cart.getBookId(), cart.getUsername());
            return environment.getProperty("REMOVED_FROM_CART");
        }
        return environment.getProperty("JWT_NOT_VALID");
    }

    @Override
    public List<CartQtyDto> getCartBooks(String token) {
        List<CartQtyDto> cartQtyDto = new ArrayList<>();
        try {
            if (jwtUtils.validateJwtToken(token)) {
                String username = jwtUtils.getUserNameFromJwtToken(token);
                List<Cart> allByUsername = cartRepository.findAllByUsername(username);
                for (Cart cart : allByUsername) {
                    if (cart.getBookQuantity() == 0)
                        cartRepository.deleteCartByBookIdAndUsername(cart.getBookId(), cart.getUsername());
                    cartQtyDto.add(new CartQtyDto(bookStoreRepository.findById(cart.getBookId()).getId(),
                            bookStoreRepository.findById(cart.getBookId()).getAuthor(),
                            bookStoreRepository.findById(cart.getBookId()).getNameOfBook(),
                            bookStoreRepository.findById(cart.getBookId()).getPicPath(),
                            bookStoreRepository.findById(cart.getBookId()).getPrice(),
                            cartRepository.findByBookIdAndUsername(cart.getBookId(), cart.getUsername()).getBookQuantity()));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return cartQtyDto;
    }

}


//    @Override
//    public Map<Book, Integer> getAllCartBooks(int userId) {
//        Map<Book, Integer> cartBooks = new HashMap<>();
//        List<Cart> allByUserId = cartRepository.findAllByUserId(userId);
//        for(Cart cart : allByUserId) {
//            if(cart.getBookQuantity() == 0)
//                cartRepository.deleteCartByBookIdAndUserId(cart.getBookId(), cart.getUserId());
//            cartBooks.put(bookStoreRepository.findById(cart.getBookId()), cart.getBookQuantity());
//        }
//        return cartBooks;
//    }



