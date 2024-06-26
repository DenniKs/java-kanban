package ru.yandex.javacourse.kanban.manager;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }
}