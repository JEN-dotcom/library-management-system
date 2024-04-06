package com.jen.library.service.serviceImplementation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jen.library.error.ObjectNotFoundException;
import com.jen.library.model.Book;
import com.jen.library.model.BorrowingRecord;
import com.jen.library.model.Patron;
import com.jen.library.repository.BorrowingRecordRepository;
import com.jen.library.service.BookService;
import com.jen.library.service.BorrowingRecordService;
import com.jen.library.service.PatronService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    @Autowired
    BorrowingRecordRepository recordRepository;

    @Autowired
    BookService bookService;

    @Autowired
    PatronService patronService;

    public ResponseEntity<Map<String, Object>> borrowBook(long bookId, Integer patronId) {
        Book bookToBorrow = bookService.getBookById(bookId);

        Map<String, Object> response = new HashMap<>();

        if (bookToBorrow.isBorrowed() == true) {
            response.put("message", "Sorry, Book has already been borrowed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        }

        Patron patron = patronService.getPatronById(patronId);
        bookToBorrow.setBorrowed(true);
        bookToBorrow = (Book) bookService.addBook(bookToBorrow).getBody().get("data");

        BorrowingRecord record = BorrowingRecord.builder()
                .patron(patron)
                .book(bookToBorrow)
                .build();

        response.put("message", "Book borrowed successfully");
        response.put("data", recordRepository.save(record));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> returnBook(long bookId, Integer patronId) {
        Book bookToReturn = bookService.getBookById(bookId);

        BorrowingRecord record = recordRepository.findByBook(bookToReturn)
                .orElseThrow(() -> new ObjectNotFoundException("Record not found"));

        if (bookToReturn.isBorrowed() == false) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This book hasn't been borrowed");
        }

        bookToReturn.setBorrowed(false);
        bookService.addBook(bookToReturn);

        record.setReturnedAt(LocalDateTime.now());
        recordRepository.save(record);

        return ResponseEntity.ok("Book returned successfully");
    }
}
