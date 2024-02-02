package kz.job4j.rest.service.impl;

import kz.job4j.rest.model.Person;
import kz.job4j.rest.repository.PersonRepository;
import kz.job4j.rest.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepository.deleteById(person.getId());
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }
}
