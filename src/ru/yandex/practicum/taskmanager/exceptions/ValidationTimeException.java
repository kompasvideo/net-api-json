package ru.yandex.practicum.taskmanager.exceptions;

public class ValidationTimeException extends RuntimeException {
    public ValidationTimeException(final String message) {
        super(message);
    }
}
