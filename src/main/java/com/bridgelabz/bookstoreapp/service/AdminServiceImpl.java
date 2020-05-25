package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.AdminDto;
import com.bridgelabz.bookstoreapp.dto.BookDto;
import com.bridgelabz.bookstoreapp.dto.LoginDto;
import com.bridgelabz.bookstoreapp.entity.Admin;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.repository.AdminRepository;
import com.bridgelabz.bookstoreapp.repository.BookStoreRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static boolean LoggedIn = false;

    public static boolean isLoggedIn() {
        return LoggedIn;
    }

    public static void setLoggedIn(boolean isLoggedIn) {
        AdminServiceImpl.LoggedIn = isLoggedIn;
    }

    @Override
    public String register(AdminDto adminDto) {
        Admin admin = converterService.convertToAdminEntity(adminDto);
        String encode = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encode);
        adminRepository.save(admin);
        return "Successfully registered";
    }

    @Override
    public String login(LoginDto loginDto) {
        if(adminRepository.findByEmailId(loginDto.getEmailId()) != null ) {
            Admin user = adminRepository.findByEmailId(loginDto.getEmailId());
            if (passwordEncoder.matches(loginDto.getPassWord(), user.getPassword())) {
                setLoggedIn(true);
                return "You are logged in";
            }
        }
        return "Invalid Email or Password";
    }

    @Override
    public String addNewBook(BookDto bookDto) {
        if(isLoggedIn()) {
            Book book = converterService.convertToBookEntity(bookDto);
            bookStoreRepository.save(book);
            return "Book successfully added";
        }
        return "You don't have permission to Add Book!!";
    }
}
