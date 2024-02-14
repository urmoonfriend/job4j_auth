package kz.job4j.rest.service.impl;

import kz.job4j.rest.model.entity.Person;
import kz.job4j.rest.repository.PersonRepository;
import kz.job4j.rest.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> create(Person person) {
        Optional<Person> result = Optional.empty();
        try {
            result = Optional.of(personRepository.save(person));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<Person> update(Person person) {
        Optional<Person> result = Optional.empty();
        try {
            var personOpt = personRepository.findById(person.getId());
            if (personOpt.isPresent()) {
                Person personToUpdate = personOpt.get();
                modelMapper.map(person, personToUpdate);
                result = Optional.of(personRepository.save(personToUpdate));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result = false;
        try {
            personRepository.deleteById(id);
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }

}
