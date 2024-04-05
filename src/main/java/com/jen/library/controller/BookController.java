package com.jen.library.controller;

import com.jen.library.model.Book;

import com.jen.library.service.Bookervice;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> saveBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/book")
    public List<Book> fetchBookList() {
        return bookService.getAllBook();
    }

    @GetMapping("/book/{id}")
    public Book fetchBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/book/author/{author}")
    public Book fetchBookByAuthor(@PathVariable String author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/book/author/{author}")
    public Book fetchBookByAuthor(@PathVariable String author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/book/isbn/{isbn}")
    public Book fetchBookByFullName(@PathVariable long isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @GetMapping("/book/title/{title}")
    public Book fetchBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/book/title/{title}")
    public Book fetchBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @DeleteMapping("/book/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer isbn) {
        return bookService.deleteBook(isbn);
    }

    @PutMapping("/book/title/{title}/{userEmail}")
    public ResponseEntity<String> borrowBook(
            @PathVariable String title,
            @PathVariable String userEmail) {
        return bookService.borrowBook(title, userEmail);
    }
}
