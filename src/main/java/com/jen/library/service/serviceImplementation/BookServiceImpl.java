package com.jen.library.service.serviceImplementation;

import com.jen.library.service.BookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jen.library.model.Book;
import com.jen.library.error.ObjectNotFoundException;

import com.jen.library.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Cacheable("allBooks")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @CacheEvict(value = "allBooks", allEntries = true)
    public ResponseEntity<Map<String, Object>> addBook(Book Book) {
        bookRepository.save(Book);
        Map<String, Object> response = new HashMap<>();

        response.put("message", "Book added successfully");
        response.put("data", Book);
        return ResponseEntity.ok( response);
    }

    @Override
    @Cacheable(value = "getBookById", key = "#id")
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book not found"));
    }

    @Override
    @CacheEvict(value = { "getBookById", "getBookByTitle", "getBookByISBN" }, key = "#isbn")
    public ResponseEntity<String> deleteBook(Integer isbn) {
        // bookRepository.deleteById(book.getId());
        return ResponseEntity.ok("Book successfully deleted");
    }

}
