package com.jen.library.controller;

import com.jen.library.model.Book;
import com.jen.library.service.BookService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> fetchBookList() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book fetchBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable("id") long id,
            @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        return bookService.deleteBook(id);
    }

}
