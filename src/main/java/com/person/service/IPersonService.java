package com.person.service;

import com.person.entities.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {

    List<Person> findAll();
    Optional<Person> findById(Long id);

    void save(Person person);

    void deleteById(Long id);
}
