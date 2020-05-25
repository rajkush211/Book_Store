package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.AdminDto;
import com.bridgelabz.bookstoreapp.dto.LoginDto;
import com.bridgelabz.bookstoreapp.entity.Admin;
import com.bridgelabz.bookstoreapp.repository.AdminRepository;
import com.bridgelabz.bookstoreapp.utility.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Admin user = adminRepository.findByEmailId(loginDto.getEmailId());
        if (passwordEncoder.matches(loginDto.getPassWord(), user.getPassword())) {
            return "You are logged in";
        }
        return "Invalid";
    }
}
