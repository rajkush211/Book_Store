package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookStoreRepository extends JpaRepository<Book, String> {

    List<Book> findByAuthor(String author);

    List<Book> findAllByOrderByPriceAsc();
}
