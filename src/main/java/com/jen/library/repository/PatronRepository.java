package com.jen.library.repository;

import com.jen.library.model.Patron;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Integer> {

}
