package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBookStoreService {

    String loadBookData();

    Page<Book> getAllBook(Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    Page<Book> getAllBookByPriceAsc(Pageable pageable);

    Page<Book> getAllBookByPriceDesc(Pageable pageable);

    String fetchBookData(MultipartFile multipartFile);

    String addNewBook(BookDto bookDto);

    String verifyUserAccount(Long userId);

    Page<Book> getAll(Pageable pageable);

    Page<Book> searchBooks(String searchText, Pageable pageable);
}
