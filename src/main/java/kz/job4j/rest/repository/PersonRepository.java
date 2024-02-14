package kz.job4j.rest.repository;

import kz.job4j.rest.model.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();

    Optional<Person> findByLogin(String login);

}