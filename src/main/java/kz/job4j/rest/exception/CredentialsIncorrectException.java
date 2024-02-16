package kz.job4j.rest.exception;

public class CredentialsIncorrectException extends Exception {
    public CredentialsIncorrectException() {
        super("Login or password is incorrect");
    }
}
