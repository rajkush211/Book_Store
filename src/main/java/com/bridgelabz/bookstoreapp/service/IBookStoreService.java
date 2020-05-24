package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookStoreService {

    String loadBookData();

    String addNewBook(BookDto bookDto);

    Page<Book> getAllBook(Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    Page<Book> getAllBookByPriceAsc(Pageable pageable);

    Page<Book> getAllBookByPriceDesc(Pageable pageable);
}
