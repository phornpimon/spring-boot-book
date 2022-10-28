package com.pornpimon.stockbackend.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.pornpimon.stockbackend.model.BookCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookRequest {
    
    @NotEmpty(message = "is Empty")
    @Size(min = 2,max = 100)
    private String bookname;
    
    private String author;

    private String publisher;

    private String booklet;

    private BookCategory bookCategory;

    private String bookDescription;

    private MultipartFile image;
}
