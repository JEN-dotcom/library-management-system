package com.jen.library.repository;

import com.jen.library.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<Book, Long> {

}
