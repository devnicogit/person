package com.person.service.impl;

import com.person.entities.Person;
import com.person.persistence.IPersonDAO;
import com.person.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personDAO.findById(id);
    }

    @Override
    public void save(Person person) {
        personDAO.save(person);
    }

    @Override
    public void deleteById(Long id) {
        personDAO.deleteById(id);
    }

    @Override
    public boolean existsByDni(String dni) {
        return personDAO.existsByDni(dni);
    }
}
