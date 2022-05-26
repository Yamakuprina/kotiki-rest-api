package ru.itmo.banks;

public class BankException extends RuntimeException{
    public BankException(String message) {
        super(message);
    }
}
