package com.pornpimon.stockbackend.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pornpimon.stockbackend.model.Book;
import com.pornpimon.stockbackend.model.BookCategory;

public interface BookRepository extends JpaRepository<Book,Long>{

    Page<Book> findByBooknameContaining(String bookname, Pageable pageable);

    Page<Book> findByBookCategory(BookCategory bookCategory, Pageable pageable);
}
