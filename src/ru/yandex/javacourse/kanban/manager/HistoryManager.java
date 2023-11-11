package ru.yandex.javacourse.kanban.manager;

import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    // Удаление задачи из истории.
    void remove(int id);

    // Удаление всей истории.
    void removeAll();

    List<Task> getHistory();
}
