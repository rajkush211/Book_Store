package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class BookStoreServiceImpl implements IBookStoreService  {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Override
    public void loadBookData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/books_data.csv"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Book book = new Book();
                book.setId(data[0]);
                book.setAuthor(data[1]);
                book.setNameOfBook(data[2]);
                book.setPicPath(data[3]);
                book.setPrice(Integer.parseInt(data[4]));
                book.setDescription(data[5]);
                bookStoreRepository.save(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
