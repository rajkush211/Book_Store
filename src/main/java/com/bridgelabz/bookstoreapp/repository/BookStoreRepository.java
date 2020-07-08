package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStoreRepository extends JpaRepository<Book, String>, PagingAndSortingRepository<Book, String> {

    Page<Book> findAllByOrderByPriceDesc(Pageable pageable);

    Book findById(int bookId);

    Page<Book> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

    void deleteById(int id);

    @Query("SELECT book FROM Book book WHERE book.author LIKE %:searchText% OR book.nameOfBook LIKE %:searchText%")
    Page<Book> findAllByAuthorContainingOrNameOfBookContaining(String searchText, Pageable pageable);

}

