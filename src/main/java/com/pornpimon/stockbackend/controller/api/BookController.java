package com.pornpimon.stockbackend.controller.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pornpimon.stockbackend.controller.request.BookRequest;
import com.pornpimon.stockbackend.exception.ValidationException;
import com.pornpimon.stockbackend.model.Book;
import com.pornpimon.stockbackend.model.BookCategory;
import com.pornpimon.stockbackend.service.BookResponse;
import com.pornpimon.stockbackend.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/sort/{field}/offset={offset}/pageSize={pageSize}")
    public BookResponse<Page<Book>> getBooksSort(@PathVariable String field, @PathVariable int offset, @PathVariable int pageSize) {
        Page<Book> allBook = bookService.getAllBookSort(field, offset, pageSize);
        return new BookResponse<Page<Book>>(allBook.getSize(), allBook);
    }

    @GetMapping("/offset={offset}/pageSize={pageSize}")
    public BookResponse<Page<Book>> getBooksPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Book> allbookpagination = bookService.getAllBookPagination(offset, pageSize);
        return new BookResponse<Page<Book>>(allbookpagination.getSize(), allbookpagination);
    }

    @GetMapping("/search/offset={offset}/pageSize={pageSize}")
    public BookResponse<Page<Book>> searchBookByBookname(@RequestParam String bookname, @PathVariable int offset, @PathVariable int pageSize) {
        Page<Book> searchBook = bookService.getBookByBookname(bookname, offset, pageSize);
        return new BookResponse<Page<Book>>(searchBook.getSize(), searchBook);
    }

    @GetMapping("/category/offset={offset}/pageSize={pageSize}")
    public BookResponse<Page<Book>> searchBookByBookCategory(@RequestParam BookCategory bookCategory, @PathVariable int offset, @PathVariable int pageSize) {
        Page<Book> categoryBook = bookService.getBookByCategory(bookCategory, offset, pageSize);
        return new BookResponse<Page<Book>>(categoryBook.getSize(), categoryBook);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Book addBook(@Valid BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(filedError -> {
                throw new ValidationException(filedError.getField() + ": " + filedError.getDefaultMessage());
            });
        }
        return bookService.createBook(bookRequest);
    }

    @PutMapping("/{id}")
    public Book editBook(@Valid BookRequest bookRequest, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(filedError -> {
                throw new ValidationException(filedError.getField() + ": " + filedError.getDefaultMessage());
            });
        }
        return bookService.updateBook(bookRequest, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
}
