package com.jen.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.jen.library.error.ObjectNotFoundException;
import com.jen.library.model.Book;
import com.jen.library.repository.BookRepository;
import com.jen.library.service.serviceImplementation.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() {

        Book book1 = new Book();
        book1.setPublicationYear(50);
        Book book2 = new Book();
        book2.setPublicationYear(60);
        List<Book> expectedBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAllBooks();

        assertEquals(expectedBooks.size(), actualBooks.size());
        assertEquals(expectedBooks.get(0), actualBooks.get(0));
        assertEquals(expectedBooks.get(1).getPublicationYear(), 60);
        assertNotEquals(expectedBooks.get(1).getPublicationYear(), 50);

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testAddBook() {

        Book book = new Book();
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Book added successfully");
        expectedResponse.put("data", book);

        when(bookRepository.save(book)).thenReturn(book);

        ResponseEntity<Map<String, Object>> responseEntity = bookService.addBook(book);
        Map<String, Object> actualResponse = responseEntity.getBody();

        assertEquals(expectedResponse.get("message"), actualResponse.get("message"));
        assertEquals(expectedResponse.get("data"), actualResponse.get("data"));

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testGetBookById_BookExists() {
        long id = 1;
        Book expectedBook = new Book();
        expectedBook.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookById(id);

        assertEquals(expectedBook, actualBook);
        assertEquals(expectedBook.getId(), 1);
        assertNotEquals(expectedBook, 2);

        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    public void testGetBookById_BookNotFound() {
        long id = 1;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> bookService.getBookById(id));

        verify(bookRepository, times(1)).findById(id);
    }
    

    @Test
    public void testUpdateBook() {
        long id = 1;
        Book existingBook = new Book();
        Book updatedBook = new Book();
        updatedBook.setId(id);
        existingBook.setPublicationYear(50);

        existingBook.setId(id);
        updatedBook.setPublicationYear(60);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Book updated successfully");
        expectedResponse.put("data", updatedBook);

        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));


        ResponseEntity<Map<String, Object>> responseEntity = bookService.updateBook(id, updatedBook);
        Map<String, Object> actualResponse = responseEntity.getBody();

        assertEquals(expectedResponse.get("message"), actualResponse.get("message"));
        assertEquals(expectedResponse.get("data"), actualResponse.get("data"));

        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    public void testDeleteBook() {  
        long id = 1;

        ResponseEntity<String> responseEntity = bookService.deleteBook(id);
        ResponseEntity<String> expectedresponseEntity = new ResponseEntity<>(HttpStatusCode.valueOf(200));

        assertEquals("Book successfully deleted", responseEntity.getBody());
        assertEquals(expectedresponseEntity.getStatusCode(), responseEntity.getStatusCode());

        verify(bookRepository, times(1)).deleteById(id);
    }
}
