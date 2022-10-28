package com.pornpimon.stockbackend.exception;

import com.pornpimon.stockbackend.model.BookCategory;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(long id) {
        super("Could Not "+ id);
    }

    public BookNotFoundException(String bookname) {
        super("Could Not "+ bookname);
    }

    public BookNotFoundException(BookCategory bookCategory) {
        super("Could Not "+ bookCategory);
    }
    
}
