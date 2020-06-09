package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.WishlistDto;
import com.bridgelabz.bookstoreapp.entity.Wishlist;
import com.bridgelabz.bookstoreapp.repository.WishlistRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishlistServiceImpl implements IWishlistService  {

    @Autowired
    private ConverterService converterService;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public String addToWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = converterService.convertToWishlistEntity(wishlistDto);
        if(wishlistRepository.existsWishlistByBookId(wishlist.getBookId()) && wishlistRepository.existsWishlistByUserId(wishlist.getUserId()))
            wishlistRepository.deleteWishlistByBookIdAndUserId(wishlist.getBookId(), wishlist.getUserId());
        wishlistRepository.save(wishlist);
        return "Added to Wishlist successfully";
    }

    @Override
    public String removeFromWishlist(WishlistDto wishlistDto) {
        Wishlist wishlist = converterService.convertToWishlistEntity(wishlistDto);
        wishlistRepository.deleteWishlistByBookIdAndUserId(wishlist.getBookId(), wishlist.getUserId());
        return "Removed from Wishlist successfully";
    }
}
