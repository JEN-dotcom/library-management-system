package com.jen.library.controller;

import com.jen.library.service.BorrowingRecordService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService recordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Map<String, Object>> borrowBook(@PathVariable long bookId, @PathVariable Integer patronId) {
        return recordService.borrowBook(bookId, patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable long bookId, @PathVariable Integer patronId) {
        return recordService.returnBook(bookId, patronId);
    }
    
}
