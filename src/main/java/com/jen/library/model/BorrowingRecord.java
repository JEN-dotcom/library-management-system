package com.jen.library.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private long id;

    @OneToOne
    @JoinColumn(name="patron_id")
    private Patron patron;

    @OneToOne
    @JoinColumn(name="book_id")
    private Book book;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime borrowedAt;

    private LocalDateTime returnedAt;
}
