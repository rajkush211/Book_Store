package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import org.springframework.stereotype.Component;

public interface IBookStoreService {

    void loadBookData();

    String addNewBook(BookDto bookDto);
}
