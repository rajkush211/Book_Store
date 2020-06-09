package com.bridgelabz.bookstoreapp.dto;

public class CartDto {

    private int userId;
    private int bookId;
    private int bookQuantity;

    public CartDto(int userId, int bookId, int bookQuantity) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookQuantity = bookQuantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
