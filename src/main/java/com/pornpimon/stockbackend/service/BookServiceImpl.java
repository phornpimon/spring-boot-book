package com.pornpimon.stockbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pornpimon.stockbackend.Repository.BookRepository;
import com.pornpimon.stockbackend.controller.request.BookRequest;
import com.pornpimon.stockbackend.exception.BookNotFoundException;
import com.pornpimon.stockbackend.model.Book;
import com.pornpimon.stockbackend.model.BookCategory;

@Service
public class BookServiceImpl implements BookService {

    private final StoregeService storegeService;
    private final BookRepository bookRepository;

    BookServiceImpl(StoregeService storegeService, BookRepository bookRepository) {
        this.storegeService = storegeService;
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> getAllBookPagination(int offset, int pageSize) {
        return bookRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public Page<Book> getAllBookSort(String field, int offset, int pageSize) {
        return bookRepository.findAll(PageRequest.of(offset, pageSize, Sort.by(field)));
    }

    @Override
    public Page<Book> getBookByBookname(String bookname, int offset, int pageSize) {
        Page<Book> book =  bookRepository.findByBooknameContaining(bookname, PageRequest.of(offset, pageSize));
        if (book.getSize() > 0) {
            return book;
        }
        throw new BookNotFoundException(bookname);
    }

    @Override
    public Page<Book> getBookByCategory(BookCategory bookCategory, int offset, int pageSize) {
       Page<Book> book = bookRepository.findByBookCategory(bookCategory, PageRequest.of(offset, pageSize));
       if (book.getSize() > 0) {
           return book;
       }
        throw new BookNotFoundException(bookCategory);
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book =  bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new BookNotFoundException(id);
    }

    @Override
    public Book createBook(BookRequest bookRequest) {
        String fileNamne = storegeService.store(bookRequest.getImage());
        Book data = new Book();
        data.setBookname(bookRequest.getBookname())
        .setAuthor(bookRequest.getAuthor())
        .setPublisher(bookRequest.getPublisher())
        .setBooklet(bookRequest.getBooklet())
        .setBookCategory(bookRequest.getBookCategory())
        .setBookDescription(bookRequest.getBookDescription())
        .setImage(fileNamne);
        return bookRepository.save(data);
    }

    @Override
    public Book updateBook(BookRequest bookRequest, Long id) {
        String fileNamne = storegeService.store(bookRequest.getImage());
        Optional<Book> book =  bookRepository.findById(id);
        if (book.isPresent()) {
            Book existingBook = book.get();
            if (fileNamne != null) {
                existingBook.setImage(fileNamne);
            }
            existingBook.setBookname(bookRequest.getBookname())
            .setAuthor(bookRequest.getAuthor())
            .setPublisher(bookRequest.getPublisher())
            .setBooklet(bookRequest.getBooklet())
            .setBookCategory(bookRequest.getBookCategory())
            .setBookDescription(bookRequest.getBookDescription())
            .setImage(fileNamne);
            return bookRepository.save(existingBook);
        }
        throw new BookNotFoundException(id);
    }

    @Override
    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new BookNotFoundException(id);
        }
        
    }
}
