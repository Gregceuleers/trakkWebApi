package com.trakknamur.demo.exceptions.models;

public class PasswordNotValidException extends Exception {

    public PasswordNotValidException() {
        super("Le password ne correspond pas à l'utilisateur fourni");
    }

    public PasswordNotValidException(String message) {
        super(message);
    }
}
