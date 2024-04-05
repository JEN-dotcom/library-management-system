package com.jen.library.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jen.library.error.GlobalExceptionHandler;
import com.jen.library.error.ObjectNotFoundException;
import com.jen.library.model.Patron;
import com.jen.library.service.PatronService;

@ExtendWith(MockitoExtension.class)
class PatronControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Mock
        private PatronService patronService;

        @InjectMocks
        private PatronController patronController;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(patronController)
                                .setControllerAdvice(new GlobalExceptionHandler())

                                .build();
        }

        @Test
        void savePatron_InvalidPatron_ReturnsBadRequest() throws Exception {

                String requestBody = "{\"fullName\": \"John\", \"email\": \"invalidemail\", \"address\": \"123 Main St\", \"age\": 15, \"contactInformation\": \"123456\"}";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        void savePatron_ValidPatron_ReturnsStatusCreated() throws Exception {

                String requestBody = "{\"fullName\": \"John Doe\", \"email\": \"johndoe@example.com\", \"address\": \"123 Main St\", \"age\": 25, \"contactInformation\": \"123456\"}";

                when(patronService.addPatron(any(Patron.class)))
                                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

                mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        void fetchPatronList_ReturnsListOfPatrons() throws Exception {

                Patron patron1 = new Patron();
                patron1.setId(1);
                patron1.setFullName("John Doe");

                Patron patron2 = new Patron();
                patron2.setId(2);
                patron2.setFullName("Jane Doe");

                List<Patron> patronList = Arrays.asList(patron1, patron2);

                when(patronService.getAllPatrons()).thenReturn(patronList);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").value("John Doe"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fullName").value("Jane Doe"));
        }

        @Test
        void fetchPatronById_ReturnsPatron() throws Exception {

                int patronId = 1;
                Patron expectedPatron = new Patron();
                expectedPatron.setId(patronId);
                expectedPatron.setFullName("John Doe");

                when(patronService.getPatronById(patronId)).thenReturn(expectedPatron);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/{id}", patronId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(patronId))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("John Doe"));
        }

        @Test
        void fetchPatronById_ReturnsNotFound() throws Exception {

                int patronId = 100;
                when(patronService.getPatronById(patronId)).thenThrow(new ObjectNotFoundException("nn"));

                assertThrows(ObjectNotFoundException.class, () -> patronService.getPatronById(patronId));
                mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/{id}", patronId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(result -> assertTrue(
                                                result.getResolvedException() instanceof ObjectNotFoundException))

                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void updatePatron_ValidPatron_ReturnsUpdatedPatron() throws Exception {

                int patronId = 1;
                Patron updatedPatron = new Patron();
                updatedPatron.setId(patronId);
                updatedPatron.setFullName("Updated Name");
                updatedPatron.setEmail("updated@example.com");

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Patron updated successfully");
                response.put("data", updatedPatron);
                ResponseEntity<Map<String, Object>> desiredResponse = ResponseEntity.ok().body(response);
                when(patronService.updatePatron(any(Integer.class), any(Patron.class))).thenReturn(desiredResponse);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/{id}", patronId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                                "{\"id\": 1, \"fullName\": \"Updated Name\", \"email\": \"updated@example.com\", \"address\": \"Updated Address\", \"age\": 30, \"contactInformation\": \"Updated Contact Info\"}"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("Patron updated successfully"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data.fullName").value("Updated Name"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("updated@example.com"));
        }

        @Test
        void deletePatron_ValidPatron_ReturnsDeletedMessage() throws Exception {

                int patronId = 1;
                String deleteMessage = "Patron deleted successfully";

                ResponseEntity<String> responseEntity = ResponseEntity.ok(deleteMessage);
                when(patronService.deletePatron(patronId)).thenReturn(responseEntity);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/{id}", patronId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().string(deleteMessage));
        }

}
