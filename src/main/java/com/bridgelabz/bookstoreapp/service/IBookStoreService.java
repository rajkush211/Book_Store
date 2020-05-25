package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IBookStoreService {

    String loadBookData();

    Page<Book> getAllBook(Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    Page<Book> getAllBookByPriceAsc(Pageable pageable);

    Page<Book> getAllBookByPriceDesc(Pageable pageable);

    String fetchBookData(MultipartFile multipartFile);
}
