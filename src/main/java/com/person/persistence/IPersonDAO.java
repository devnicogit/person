package com.person.persistence;

import com.person.entities.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonDAO {

    List<Person> findAll();
    Optional<Person> findById(Long id);

    void save(Person person);

    void deleteById(Long id);

    boolean existsByDni(String dni);
}
