package test.ru.yandex.javacourse.kanban.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.manager.InMemoryHistoryManager;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestInMemoryHistoryManager {
    InMemoryHistoryManager historyManager;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Task 1", "Description for Task 1", 1, Status.NEW);
        task2 = new Task("Task 2", "Description for Task 2", 2, Status.NEW);
        task3 = new Task("Task 3", "Description for Task 3", 3, Status.NEW);
    }

    @Test
    // Проверяет добавление задач в историю.
    void testAdd() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));
    }

    @Test
    // Проверяет удаление задачи из истории.
    void testRemove() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task3, history.get(1));
        assertFalse(history.contains(task2));
    }

    @Test
    // Проверяет получение истории задач.
    void testGetHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    // Проверяет удаление задачи из начала списка истории.
    void testRemoveFromHead() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task2, history.get(0));
        assertEquals(task3, history.get(1));
    }

    @Test
    // Проверяет удаление задачи из конца списка истории.
    void testRemoveFromTail() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    // Проверяет удаление несуществующей задачи (должно игнорироваться).
    void testRemoveNonExistent() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(999); // Несуществующий идентификатор.

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    // Проверяет добавление дубликата задачи (дубликат перемещается в конец списка).
    void testAddDuplicate() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1); // Добавить дубликат.

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task2, history.get(0)); // Задача 2 должна быть первой, поскольку задача 1 была добавлена повторно.
        assertEquals(task1, history.get(1)); // Задача 1 должна быть последней.
    }
}
