package kz.job4j.rest.service;

import kz.job4j.rest.model.dto.ChangePasswordDto;
import kz.job4j.rest.model.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();

    Optional<Person> create(Person person);

    Optional<Person> update(Person person);

    boolean deleteById(Integer id);

    Optional<Person> findById(int id);

    Optional<Person> findByLogin(String login);

    Optional<Person> changePassword(ChangePasswordDto dto);
}
