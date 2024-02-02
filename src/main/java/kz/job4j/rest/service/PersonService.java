package kz.job4j.rest.service;

import kz.job4j.rest.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();

    Person save(Person person);

    void delete(Person person);

    Optional<Person> findById(int id);
}
