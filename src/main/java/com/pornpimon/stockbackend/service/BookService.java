package com.pornpimon.stockbackend.service;

import org.springframework.data.domain.Page;

import com.pornpimon.stockbackend.controller.request.BookRequest;
import com.pornpimon.stockbackend.model.Book;
import com.pornpimon.stockbackend.model.BookCategory;

public interface BookService {

    Page<Book> getAllBookPagination(int offset, int pageSize);

    Page<Book> getAllBookSort(String field, int offset, int pageSize);

    Page<Book> getBookByBookname(String bookname, int offset, int pageSize);

    Page<Book> getBookByCategory(BookCategory bookCategory, int offset, int pageSize);

    Book getBookById(Long id);

    Book createBook(BookRequest BookRequest);

    Book updateBook(BookRequest BookRequest, Long id);

    void deleteBook(Long id);
}
