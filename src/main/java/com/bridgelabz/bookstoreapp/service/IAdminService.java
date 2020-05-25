package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.AdminDto;
import com.bridgelabz.bookstoreapp.dto.LoginDto;

public interface IAdminService {

    String register(AdminDto adminDto);

    String login(LoginDto loginDto);
}
