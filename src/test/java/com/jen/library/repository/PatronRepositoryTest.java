package com.jen.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.jen.library.model.Patron;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PatronRepositoryTest {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private TestEntityManager entityManager;

    // @BeforeAll
    // void BeforeAll() {

    // }

    // @BeforeEach
    // void setUp() {

    // }

    @Test
    public void whenFindById_thenReturnPatron() {
        Patron patron = Patron.builder()
                .fullName("John Doe")
                .age(50)

                .address("Dubai")
                .email("jdoe@example.com")
                .contactInformation("Uae")
                .build();

        patron = entityManager.persistAndFlush(patron);

        Patron patronfound = patronRepository.findById(patron.getId()).get();
        assertEquals(patronfound.getAge(), patron.getAge());
    }
}