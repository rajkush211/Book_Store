package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;

public interface IBookStoreService {

    String loadBookData();

    String addNewBook(BookDto bookDto);
}
