package kz.job4j.rest.exception;

import kz.job4j.rest.model.entity.Person;

public class PersonExistsException extends Exception {
    public PersonExistsException(String person) {
        super(String.format("Person with login: %s already exists.", person));
    }
}
