package com.jen.library.controller;

import com.jen.library.model.Patron;

import com.jen.library.service.PatronService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> savePatron(@Valid @RequestBody Patron patron) {
        return patronService.addPatron(patron);
    }

    @GetMapping
    public List<Patron> fetchPatronList() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public Patron fetchPatronById(@PathVariable("id") Integer id) {
        return patronService.getPatronById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePatron(@PathVariable("id") Integer id,
            @Valid @RequestBody Patron patron) {
        return patronService.updatePatron(id, patron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletepatron(@PathVariable("id") Integer id) {
        return patronService.deletePatron(id);
    }
}
