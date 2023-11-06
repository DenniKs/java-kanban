package ru.yandex.javacourse.kanban.manager;

public class Managers {
    public  static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
