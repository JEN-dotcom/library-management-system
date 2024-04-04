package com.jen.library.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "title", "author" }),
        @UniqueConstraint(columnNames = { "isbn" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @Column(name = "book_id")
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    @Length(min = 2, max = 30, message = "Name should be at least 2 characters and not more than 30")
    private String author;

    @Min(value = 1400, message = "Invalid publication year")
    private int publicationYear;

    private boolean borrowed = false;

    @NotNull
    @Column(name = "isbn")
    public Integer isbn;
}
