package kz.job4j.rest.controller;

import kz.job4j.rest.exception.PersonExistsException;
import kz.job4j.rest.exception.PersonNotFoundException;
import kz.job4j.rest.model.entity.Person;
import kz.job4j.rest.model.response.ResultMessage;
import kz.job4j.rest.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/")
    public ResponseEntity<ResultMessage<List<Person>>> findAll() {
        return ResponseEntity.ok(ResultMessage.success(personService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage<Person>> findById(@PathVariable int id) {
        var personOpt = personService.findById(id);
        return personOpt.map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResultMessage.error(new PersonNotFoundException(id).getMessage())));
    }

    @PostMapping("/")
    public ResponseEntity<ResultMessage<Person>> create(@RequestBody Person personRequest) {
        return personService.create(personRequest).map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ResultMessage.error(new PersonExistsException(personRequest.getLogin()).getMessage())));
    }

    @PutMapping("/")
    public ResponseEntity<ResultMessage<Person>> update(@RequestBody Person personRequest) {
        return personService.update(personRequest).map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ResultMessage.error(new PersonExistsException(personRequest.getLogin()).getMessage())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultMessage<Boolean>> delete(@PathVariable int id) {
        boolean result = personService.deleteById(id);
        ResponseEntity<ResultMessage<Boolean>> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResultMessage.error(new PersonNotFoundException(id).getMessage()));
        if (result) {
            response = ResponseEntity.ok(ResultMessage.success());
        }
        return response;
    }
}