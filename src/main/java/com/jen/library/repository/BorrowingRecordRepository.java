package com.jen.library.repository;

import com.jen.library.model.Book;
import com.jen.library.model.BorrowingRecord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    
    Optional<BorrowingRecord> findByBook(Book book);
}
