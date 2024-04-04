package com.jen.library.service.serviceImplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jen.library.model.Patron;
import com.jen.library.service.PatronService;
import com.jen.library.error.ObjectNotFoundException;

import com.jen.library.repository.PatronRepository;

@Service
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Override
    @Cacheable("allPatron")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    @CacheEvict(value = "allPatron", allEntries = true)
    public ResponseEntity<Map<String, Object>> addPatron(Patron patron) {
        patronRepository.save(patron);

        Map<String, Object> response = new HashMap<>();

        response.put("message", "patron added successfully");
        response.put("data", patron);
        return ResponseEntity.ok( response);
    }

    @Override
    @Cacheable(value = "getPatronById", key = "#id")
    public Patron getPatronById(Integer id) {
        return patronRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("patron not found"));
    }

    @Override
    @CacheEvict(value = { "getPatronById" }, key = "#id")
    public ResponseEntity<Map<String, Object>> updatePatron(Integer id, Patron patronToUpdate) {
        Patron patron = getPatronById(id);

        patron.setFullName(patronToUpdate.getFullName());
        patron.setAddress(patronToUpdate.getAddress());
        patron.setAge(patronToUpdate.getAge());
        patron.setEmail(patronToUpdate.getEmail());

        patronRepository.save(patron);

        Map<String, Object> response = new HashMap<>();

        response.put("message", "patron added successfully");
        response.put("data", patron);
        return ResponseEntity.ok( response);
    }

    @Override
    @CacheEvict(value = { "getPatronById" }, key = "#id")
    public ResponseEntity<String> deletePatron(Integer id) {
        patronRepository.deleteById(id);
        return ResponseEntity.ok("patron successfully deleted");
    }

}
