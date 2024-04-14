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
import com.jen.library.model.Book;
import com.jen.library.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Mock
        private BookService bookService;

        @InjectMocks
        private BookController bookController;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                                .setControllerAdvice(new GlobalExceptionHandler())
                                .build();
        }

        @Test
        void saveBook_InvalidBook_ReturnsBadRequest() throws Exception {

                String requestBody = "{\"title\": \"Java\", \"author\": \"John E\", \"publicationYear\": 1000, \"isbn\": 15}";

                mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        void saveBook_ValidBook_ReturnsStatusCreated() throws Exception {

                String requestBody = "{\"title\": \"Java\", \"author\": \"John E\", \"publicationYear\": 1500, \"isbn\": 15}";

                when(bookService.addBook(any(Book.class)))
                                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

                mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        void fetchBookList_ReturnsListOfBooks() throws Exception {

                Book book1 = new Book();
                book1.setId(1);
                book1.setAuthor("John Doe");

                Book book2 = new Book();
                book2.setId(2);
                book2.setAuthor("Jane Doe");

                List<Book> BookList = Arrays.asList(book1, book2);

                when(bookService.getAllBooks()).thenReturn(BookList);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("John Doe"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Jane Doe"));
        }

        @Test
        void fetchBookById_ReturnsBook() throws Exception {

                int bookId = 1;
                Book expectedBook = new Book();
                expectedBook.setId(bookId);
                expectedBook.setAuthor("John Doe");

                when(bookService.getBookById(bookId)).thenReturn(expectedBook);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("John Doe"));
        }

        @Test
        void fetchBookById_ReturnsNotFound() throws Exception {

                int bookId = 100;
                when(bookService.getBookById(bookId)).thenThrow(new ObjectNotFoundException("Book not found"));

                assertThrows(ObjectNotFoundException.class, () -> bookService.getBookById(bookId));
                mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(result -> assertTrue(
                                                result.getResolvedException() instanceof ObjectNotFoundException))

                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void updateBook_ValidBook_ReturnsUpdatedBook() throws Exception {

                long bookId = 1;
                Book updatedBook = new Book();
                updatedBook.setId(bookId);
                updatedBook.setAuthor("Updated Name");
                updatedBook.setPublicationYear(1500);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Book updated successfully");
                response.put("data", updatedBook);
                ResponseEntity<Map<String, Object>> desiredResponse = ResponseEntity.ok().body(response);
                when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(desiredResponse);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"title\": \"Java\", \"author\": \"John E\", \"publicationYear\": 1500, \"isbn\": 15}"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book updated successfully"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author").value("Updated Name"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.data.publicationYear").value(1500))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void deleteBook_ValidBook_ReturnsDeletedMessage() throws Exception {

                int BookId = 1;
                String deleteMessage = "Book deleted successfully";

                ResponseEntity<String> responseEntity = ResponseEntity.ok(deleteMessage);
                when(bookService.deleteBook(BookId)).thenReturn(responseEntity);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", BookId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().string(deleteMessage));
        }

}
