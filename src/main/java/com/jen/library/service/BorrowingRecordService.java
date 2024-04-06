package com.jen.library.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface BorrowingRecordService {
    ResponseEntity<Map<String, Object>> borrowBook(long bookId, Integer patronId);

    ResponseEntity<String> returnBook(long bookId, Integer patronId);
}
