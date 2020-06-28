package com.bridgelabz.bookstoreapp.dto;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//@JsonDeserialize(as = LoginDto.class)
public class LoginDto {

    String emailId;
    String passWord;

    public LoginDto() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
