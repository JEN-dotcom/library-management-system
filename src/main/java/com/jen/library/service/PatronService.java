package com.jen.library.service;

import com.jen.library.model.Patron;


import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PatronService {

    public List<Patron> getAllPatrons();

    public Patron getPatronById(Integer id);

    public ResponseEntity<String> deletePatron(Integer id);

    public ResponseEntity<Map<String, Object>> addPatron(Patron patron);

    public ResponseEntity<Map<String, Object>> updatePatron(Integer id, Patron patron);

}
