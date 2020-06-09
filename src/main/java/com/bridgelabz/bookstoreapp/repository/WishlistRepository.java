package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    boolean existsWishlistByUserId(int userId);
    boolean existsWishlistByBookId(int bookId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Wishlist wishlist WHERE wishlist.bookId = :bookId AND wishlist.userId = :userId")
    void deleteWishlistByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") int userId);
}
