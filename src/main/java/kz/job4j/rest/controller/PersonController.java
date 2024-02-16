package kz.job4j.rest.controller;

import kz.job4j.rest.exception.CredentialsIncorrectException;
import kz.job4j.rest.exception.PersonExistsException;
import kz.job4j.rest.exception.PersonNotFoundException;
import kz.job4j.rest.model.dto.ChangePasswordDto;
import kz.job4j.rest.model.entity.Person;
import kz.job4j.rest.model.response.ResultMessage;
import kz.job4j.rest.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<ResultMessage<List<Person>>> findAll() {
        return ResponseEntity.ok(ResultMessage.success(personService.findAll()));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResultMessage<Person>> signUp(@Valid @RequestBody Person personRequest) {
        personRequest.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        return personService.update(personRequest).map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ResultMessage.error(new PersonExistsException(personRequest.getLogin()).getMessage())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultMessage<Person>> findById(@PathVariable int id) {
        return personService.findById(id)
                .map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new PersonNotFoundException(id).getMessage()));
    }

    @PutMapping("/")
    public ResponseEntity<ResultMessage<Person>> update(@Valid @RequestBody Person personRequest) {
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

    @PatchMapping("/")
    public ResponseEntity<ResultMessage<Person>> patch(@Valid @RequestBody ChangePasswordDto request) {
        return personService.changePassword(request)
                .map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, new CredentialsIncorrectException().getMessage()));
    }
}