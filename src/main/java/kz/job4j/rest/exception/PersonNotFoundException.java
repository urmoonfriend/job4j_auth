package kz.job4j.rest.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(Integer id) {
        super(String.format("Person with id: %d not found.", id));
    }
}
