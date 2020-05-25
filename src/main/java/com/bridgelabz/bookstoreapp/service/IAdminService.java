package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.AdminDto;
import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.dto.LoginDto;
import org.springframework.web.multipart.MultipartFile;

public interface IAdminService {

    String register(AdminDto adminDto);

    String login(LoginDto loginDto);

    String addNewBook(BookDto bookDto);

    String fetchBookData(MultipartFile multipartFile);
}
