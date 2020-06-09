package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.WishlistDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.Wishlist;

import java.util.List;

public interface IWishlistService {

    String addToWishlist(WishlistDto wishlistDto);

    String removeFromWishlist(WishlistDto wishlistDto);

    List<Book> getAllBooksList(int userId);
}