package com.pornpimon.stockbackend.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse<T> {
    
    int totalBook;
    T Books;
}
