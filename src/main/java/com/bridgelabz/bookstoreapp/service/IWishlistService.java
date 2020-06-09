package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.WishlistDto;
import com.bridgelabz.bookstoreapp.entity.Wishlist;

public interface IWishlistService {

    String addToWishlist(WishlistDto wishlistDto);

    String removeFromWishlist(WishlistDto wishlistDto);
}