package com.person.persistence.impl;

import com.person.entities.Person;
import com.person.persistence.IPersonDAO;
import com.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAOImpl implements IPersonDAO {

    @Autowired
    private PersonRepository personRepository;
    @Override
    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public boolean existsByDni(String dni) {
        return personRepository.existsByDni(dni);
    }
}
