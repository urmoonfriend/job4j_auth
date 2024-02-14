package kz.job4j.rest.controller;

import kz.job4j.rest.exception.PersonExistsException;
import kz.job4j.rest.exception.PersonNotFoundException;
import kz.job4j.rest.model.entity.Person;
import kz.job4j.rest.model.response.ResultMessage;
import kz.job4j.rest.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public ResponseEntity<ResultMessage<List<Person>>> findAll() {
        return ResponseEntity.ok(ResultMessage.success(personService.findAll()));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResultMessage<Person>> signUp(@RequestBody Person personRequest) {
        personRequest.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        return personService.update(personRequest).map(person -> ResponseEntity.ok(ResultMessage.success(person)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ResultMessage.error(new PersonExistsException(personRequest.getLogin()).getMessage())));
    }

    /*
    @PostMapping("/login")
    public ResponseEntity<ResultMessage<String>> authenticateUser(@RequestBody Person loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()
                    )
            );
            Person user = (Person) authentication.getPrincipal();
            String token = JWT.create()
                    .withSubject(user.getLogin())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWTAuthenticationFilter.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(JWTAuthenticationFilter.SECRET.getBytes()));

            return ResponseEntity.ok().header(JWTAuthenticationFilter.HEADER_STRING,
                    JWTAuthenticationFilter.TOKEN_PREFIX + token).body(
                            ResultMessage.success("Token generated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResultMessage.error("Authentication failed: " + e.getMessage()));
        }
    }*/

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