package com.bridgelabz.bookstoreapp.utility;

import com.bridgelabz.bookstoreapp.dto.AdminDto;
import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.entity.Admin;
import com.bridgelabz.bookstoreapp.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterService {

    @Autowired
    private ModelMapper modelMapper;

    public BookDto convertToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book convertToBookEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    public AdminDto convertToAdminDto(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    public Admin convertToAdminEntity(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
}
