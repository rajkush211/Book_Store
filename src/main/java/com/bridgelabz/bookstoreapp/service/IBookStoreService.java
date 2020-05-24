package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Book;

import java.util.List;

public interface IBookStoreService {

    String loadBookData();

    String addNewBook(BookDto bookDto);

    List<Book> getAllBook();

    List<Book> findByAuthor(String author);

    List<Book> getAllBookByPriceAsc();

    List<Book> getAllBookByPriceDesc();
}
