package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.repository.BookStoreRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class BookStoreServiceImpl implements IBookStoreService  {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private ConverterService converterService;

    @Override
    public String loadBookData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/books_data.csv"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Book book = new Book();
                book.setAuthor(data[1]);
                book.setNameOfBook(data[2]);
                book.setPicPath(data[3]);
                book.setPrice(Integer.parseInt(data[4]));
                IntStream.range(6, data.length - 1).forEach(column -> data[5] += "," + data[column]);
                book.setDescription(data[5]);
                bookStoreRepository.save(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CSV file Loaded Successfully";
    }

    @Override
    public String addNewBook(BookDto bookDto) {
        Book book = converterService.convertToBookEntity(bookDto);
        bookStoreRepository.save(book);
        return "Book successfully added";
    }

    @Override
    public List<Book> getAllBook() {
        return bookStoreRepository.findAll();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookStoreRepository.findByAuthor(author);
    }
}
