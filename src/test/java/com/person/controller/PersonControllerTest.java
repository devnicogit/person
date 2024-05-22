package com.person.controller;

import com.person.dto.PersonDTO;
import com.person.entities.Person;
import com.person.service.IPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class PersonControllerTest {
    @Mock
    private IPersonService personService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.setNombre("Adrian");
        person.setApellido("Santillan");
        person.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        person.setDni("12345678");

        when(personService.findById(id)).thenReturn(Optional.of(person));

        ResponseEntity<?> response = personController.findById(id);

        assertEquals(200, response.getStatusCodeValue());
        PersonDTO personDTO = (PersonDTO) response.getBody();
        assertNotNull(personDTO);
        assertEquals(person.getNombre(), personDTO.getNombre());
    }

    @Test
    void testFindAll() {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNombre("Adrian");
        person1.setApellido("Santillan");
        person1.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        person1.setDni("12345678");

        Person person2 = new Person();
        person2.setId(2L);
        person2.setNombre("Adrian");
        person2.setApellido("Santillan");
        person2.setFechaNacimiento(LocalDate.of(1992, 2, 2));
        person2.setDni("87654321");

        when(personService.findAll()).thenReturn(Arrays.asList(person1, person2));

        ResponseEntity<?> response = personController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        List<PersonDTO> personList = (List<PersonDTO>) response.getBody();
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @Test
    void testSave() throws URISyntaxException {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNombre("Adrian");
        personDTO.setApellido("Santillan");
        personDTO.setFechaNacimiento("1990-01-01");
        personDTO.setDni("12345678");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(personService.existsByDni(personDTO.getDni())).thenReturn(false);

        ResponseEntity<?> response = personController.save(personDTO, bindingResult);

        assertEquals(201, response.getStatusCodeValue());
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals("Registro creado exitosamente", responseBody.get("message"));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        PersonDTO personDTO = new PersonDTO();
        personDTO.setNombre("Adrian");
        personDTO.setApellido("Santillan");
        personDTO.setFechaNacimiento("1990-01-01");
        personDTO.setDni("12345678");

        Person existingPerson = new Person();
        existingPerson.setId(id);
        existingPerson.setNombre("Adrian");
        existingPerson.setApellido("Santillan");
        existingPerson.setFechaNacimiento(LocalDate.of(1985, 5, 5));
        existingPerson.setDni("12345678");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(personService.findById(id)).thenReturn(Optional.of(existingPerson));
        when(personService.existsByDni(personDTO.getDni())).thenReturn(false);

        ResponseEntity<?> response = personController.update(id, personDTO, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals("Registro actualizado exitosamente", responseBody.get("message"));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        Person person = new Person();
        person.setId(id);

        when(personService.findById(id)).thenReturn(Optional.of(person));

        ResponseEntity<?> response = personController.delete(id);

        assertEquals(200, response.getStatusCodeValue());
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals("Registro eliminado exitosamente", responseBody.get("message"));
    }
}
