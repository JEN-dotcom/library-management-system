package com.jen.library.service.serviceImplementation;

import com.jen.library.service.BookService;

import jakarta.transaction.Transactional;

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
    @CacheEvict(value = { "getBookById" }, key = "#id")
    public ResponseEntity<String> deleteBook(long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book successfully deleted");
    }

    @Transactional
    @Override
    @CacheEvict(value = { "getBookById" }, key = "#id")
    public ResponseEntity<Map<String, Object>> updateBook(long id, Book bookToUpdate) {
        Book book = getBookById(id);

        book.setAuthor(bookToUpdate.getAuthor());
        book.setIsbn(bookToUpdate.getIsbn());
        book.setPublicationYear(bookToUpdate.getPublicationYear());
        book.setTitle(bookToUpdate.getTitle());
        Map<String, Object> response = new HashMap<>();

        response.put("message", "book updated successfully");
        response.put("data", bookRepository.save(book));
        return ResponseEntity.ok( response);
    }

}
