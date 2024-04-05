package com.jen.library.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.jen.library.model.Book;


public interface BookService {

    public List<Book> getAllBooks();

    public Book getBookById(long id);

    public ResponseEntity<String> deleteBook(long id);

    public ResponseEntity<Map<String, Object>> addBook(Book book);
    
    public ResponseEntity<Map<String, Object>> updateBook(long id, Book book);

}
