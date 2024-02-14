package kz.job4j.rest.service;

import kz.job4j.rest.model.entity.Person;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();

    Optional<Person> create(Person person);

    Optional<Person> update(Person person);

    boolean deleteById(Integer id);

    Optional<Person> findById(int id);

    Optional<Person> findByLogin(String login);

    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;

}
