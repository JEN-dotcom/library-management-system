package com.jen.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.jen.library.error.ObjectNotFoundException;
import com.jen.library.model.Patron;
import com.jen.library.repository.PatronRepository;
import com.jen.library.service.serviceImplementation.PatronServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;
    
    @InjectMocks
    private PatronServiceImpl patronService;

    @Test
    public void testGetAllPatrons() {

        Patron patron1 = new Patron();
        patron1.setAge(50);
        Patron patron2 = new Patron();
        patron2.setAge(60);
        List<Patron> expectedPatrons = Arrays.asList(patron1, patron2);

        when(patronRepository.findAll()).thenReturn(expectedPatrons);

        List<Patron> actualPatrons = patronService.getAllPatrons();

        assertEquals(expectedPatrons.size(), actualPatrons.size());
        assertEquals(expectedPatrons.get(0), actualPatrons.get(0));
        assertEquals(expectedPatrons.get(1).getAge(), 60);
        assertNotEquals(expectedPatrons.get(1).getAge(), 50);

        verify(patronRepository, times(1)).findAll();
    }

    @Test
    public void testAddPatron() {

        Patron patron = new Patron();
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "patron added successfully");
        expectedResponse.put("data", patron);

        when(patronRepository.save(patron)).thenReturn(patron);

        ResponseEntity<Map<String, Object>> responseEntity = patronService.addPatron(patron);
        Map<String, Object> actualResponse = responseEntity.getBody();

        assertEquals(expectedResponse.get("message"), actualResponse.get("message"));
        assertEquals(expectedResponse.get("data"), actualResponse.get("data"));

        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    public void testGetPatronById_PatronExists() {
        Integer id = 1;
        Patron expectedPatron = new Patron();
        expectedPatron.setId(id);

        when(patronRepository.findById(id)).thenReturn(Optional.of(expectedPatron));

        Patron actualPatron = patronService.getPatronById(id);

        assertEquals(expectedPatron, actualPatron);
        assertEquals(expectedPatron.getId(), 1);
        assertNotEquals(expectedPatron, 2);

        verify(patronRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPatronById_PatronNotFound() {
        Integer id = 1;

        when(patronRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> patronService.getPatronById(id));

        verify(patronRepository, times(1)).findById(id);
    }
    

    @Test
    public void testUpdatePatron() {
        Integer id = 1;
        Patron existingPatron = new Patron();
        Patron updatedPatron = new Patron();
        updatedPatron.setId(id);
        existingPatron.setAge(50);

        existingPatron.setId(id);
        updatedPatron.setAge(60);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "patron updated successfully");
        expectedResponse.put("data", updatedPatron);

        when(patronRepository.save(any(Patron.class))).thenReturn(updatedPatron);
        when(patronRepository.findById(id)).thenReturn(Optional.of(existingPatron));


        ResponseEntity<Map<String, Object>> responseEntity = patronService.updatePatron(id, updatedPatron);
        Map<String, Object> actualResponse = responseEntity.getBody();

        assertEquals(expectedResponse.get("message"), actualResponse.get("message"));
        assertEquals(expectedResponse.get("data"), actualResponse.get("data"));

        verify(patronRepository, times(1)).findById(id);
        verify(patronRepository, times(1)).save(updatedPatron);
    }

    @Test
    public void testDeletePatron() {  
        Integer id = 1;

        ResponseEntity<String> responseEntity = patronService.deletePatron(id);
        ResponseEntity<String> expectedresponseEntity = new ResponseEntity<>(HttpStatusCode.valueOf(200));

        assertEquals("patron successfully deleted", responseEntity.getBody());
        assertEquals(expectedresponseEntity.getStatusCode(), responseEntity.getStatusCode());

        verify(patronRepository, times(1)).deleteById(id);
    }
}
