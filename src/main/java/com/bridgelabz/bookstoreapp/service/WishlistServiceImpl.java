package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.WishlistDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishlistServiceImpl implements IWishlistService  {


    @Override
    public String addToWishlist(WishlistDto wishlistDto) {
        return null;
    }

    @Override
    public String removeFromWishlist(WishlistDto wishlistDto) {
        return null;
    }
}
