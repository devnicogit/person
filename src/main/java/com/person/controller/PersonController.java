package com.person.controller;


import com.person.dto.PersonDTO;
import com.person.entities.Person;
import com.person.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/person")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Person> personOptional = personService.findById(id);
        if(personOptional.isPresent()){
            Person person = personOptional.get();
            PersonDTO personDTO = PersonDTO.builder()
                    .id(person.getId())
                    .nombre(person.getNombre())
                    .apellido(person.getApellido())
                    .fechaNacimiento(String.valueOf(person.getFechaNacimiento()))
                    .dni(person.getDni())
                    .build();
            return ResponseEntity.ok(personDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<PersonDTO> personList = personService.findAll()
                .stream()
                .map(person -> PersonDTO.builder()
                        .id(person.getId())
                        .nombre(person.getNombre())
                        .apellido(person.getApellido())
                        .fechaNacimiento(String.valueOf(person.getFechaNacimiento()))
                        .dni(person.getDni())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(personList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody PersonDTO personDTO,  BindingResult result) throws URISyntaxException {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // Verificar si ya existe una persona con el mismo DNI
        if (personService.existsByDni(personDTO.getDni())) {
            return ResponseEntity.badRequest().body("Ya existe una persona con el mismo DNI");
        }

        // Guardar la persona si pasa todas las validaciones
        personService.save(Person.builder()
                .nombre(personDTO.getNombre())
                .apellido(personDTO.getApellido())
                .fechaNacimiento(LocalDate.parse(personDTO.getFechaNacimiento()))
                .dni(personDTO.getDni())
                .build());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Registro creado exitosamente");

        return ResponseEntity.created(new URI("/api/person/save")).body(responseBody);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO, BindingResult result) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // Verificar si la persona con el ID dado existe
        Optional<Person> existingPersonOptional = personService.findById(id);
        if (!existingPersonOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Person existingPerson = existingPersonOptional.get();

        // Verificar si ya existe una persona con el mismo DNI (excepto la persona actual)
        if (!existingPerson.getDni().equals(personDTO.getDni()) && personService.existsByDni(personDTO.getDni())) {
            return ResponseEntity.badRequest().body("Ya existe una persona con el mismo DNI");
        }

        existingPerson.setNombre(personDTO.getNombre());
        existingPerson.setApellido(personDTO.getApellido());
        existingPerson.setFechaNacimiento(LocalDate.parse(personDTO.getFechaNacimiento()));
        existingPerson.setDni(personDTO.getDni());

        personService.save(existingPerson);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Registro actualizado exitosamente");
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // Verificar si la persona con el ID dado existe
        Optional<Person> existingPersonOptional = personService.findById(id);
        if (!existingPersonOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personService.deleteById(id);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Registro eliminado exitosamente");

        return ResponseEntity.ok(responseBody);
    }



}
