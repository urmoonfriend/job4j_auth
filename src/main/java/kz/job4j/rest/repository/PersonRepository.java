package kz.job4j.rest.repository;

import org.springframework.data.repository.CrudRepository;
import kz.job4j.rest.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();
}