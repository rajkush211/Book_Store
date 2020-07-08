package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.repository.BookStoreRepository;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

@Service
@Transactional
@PropertySource("classpath:message.properties")
public class BookStoreServiceImpl implements IBookStoreService {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    IElasticsearchService elasticsearchService;

    @Autowired
    private Environment environment;

    @Override
    public String loadBookData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/books_data.csv"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Book book = new Book();
                book.setAuthor(data[1].replaceAll("'", ""));
                book.setNameOfBook(data[2].replaceAll("'", ""));
                book.setQuantity(Integer.parseInt(data[3].replaceAll("'", "")));
                book.setPicPath(data[4].replaceAll("'", ""));
                book.setPrice(Integer.parseInt(data[5].replaceAll("'", "")));
                IntStream.range(7, data.length - 1).forEach(column -> data[6] += "," + data[column]);
                book.setDescription(data[6]);
                bookStoreRepository.save(book);
                elasticsearchService.createBook(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return environment.getProperty("CSV_FILE_LOADED");
    }

    @Override
    public Page<Book> getAllBook(Pageable pageable) {

        return bookStoreRepository.findAll(pageable);
    }

    @Override
    public Page<Book> findByAuthor(String author, Pageable pageable) {
        return bookStoreRepository.findByAuthor(author, pageable);
    }

    @Override
    public Page<Book> getAllBookByPriceAsc(Pageable pageable) {
        return bookStoreRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Book> getAllBookByPriceDesc(Pageable pageable) {
        return bookStoreRepository.findAllByOrderByPriceDesc(pageable);
    }

    @Override
    public String addNewBook(BookDto bookDto) {
        try {
            Book book = converterService.convertToBookEntity(bookDto);
            bookStoreRepository.save(book);
            elasticsearchService.createBook(book);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return environment.getProperty("BOOK_ADDED");
    }

    @Override
    public String fetchBookData(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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
        return environment.getProperty("CSV_FILE_LOADED");
    }

    @Override
    public String verifyUserAccount(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.get().setVerified(true);
        userRepository.save(user.get());
        return environment.getProperty("ACCOUNT_IS_VERIFIED");
    }

    @Override
    public List<Book> getAll(Pageable pageable) {
        return (List<Book>) bookStoreRepository.findAll();
    }

    // Method to search book by text given by user
    @Override
    public List<Book> searchBooks(String searchText) throws IOException {
        List<Book> searchList = new ArrayList<>();
        searchList = elasticsearchService.searchBook(searchText);
        return searchList;
    }
    public String updateBook(int id, BookDto bookDto) throws IOException {
        Book book = converterService.convertToBookEntity(bookDto);
        Optional<Book> bookFind = Optional.ofNullable(bookStoreRepository.findById(id));
        if (bookFind.isPresent()) {
            book.setId(id);
            bookStoreRepository.save(book);
            elasticsearchService.updateBook(id, book);
            return environment.getProperty("UPDATED_BOOK");
        }else {
            return environment.getProperty("BOOK_NOT_FOUND");
        }
    }

    @Override
    public String deleteBook(int id) throws IOException {
        Book bookFind = bookStoreRepository.findById(id);
        if (bookFind != null) {
            bookStoreRepository.deleteById(id);
            elasticsearchService.deleteBook(id);
            return environment.getProperty("DELETED_BOOK");
        }else {
            return environment.getProperty("BOOK_NOT_FOUND");
        }
    }
}
