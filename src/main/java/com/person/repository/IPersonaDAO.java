package com.person.repository;

import com.person.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonaDAO extends CrudRepository<Person, Long> {
}
