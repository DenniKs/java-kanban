package test.ru.yandex.javacourse.kanban.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.manager.TaskController;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskController {
    private TaskController taskController;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskController = new TaskController();
        task1 = new Task("Task 1", "Description for Task 1", 1, Status.NEW);
        task2 = new Task("Task 2", "Description for Task 2", 2, Status.NEW);
    }

    @Test
    // Проверяет добавление задачи.
    void testAdd() {
        Task addedTask = taskController.add(task1);
        assertNotNull(addedTask);
        assertEquals(task1.getName(), addedTask.getName());
        assertEquals(task1.getDescription(), addedTask.getDescription());
        assertNotEquals(0, addedTask.getId());
    }

    @Test
    // Проверяет поиск задачи по идентификатору.
    void testFindById() {
        taskController.add(task1);
        Task foundTask = taskController.findById(task1.getId());
        assertEquals(task1, foundTask);
    }

    @Test
    // Проверяет получение всех задач.
    void testFindAll() {
        taskController.add(task1);
        taskController.add(task2);
        ArrayList<Task> tasks = taskController.findAll();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    // Проверяет обновление задачи.
    void testUpdate() {
        taskController.add(task1);
        Task updatedTask = new Task("Updated Task 1", "Updated Description for Task 1", task1.getId(), Status.IN_PROGRESS);
        taskController.update(updatedTask);
        Task foundTask = taskController.findById(updatedTask.getId());
        assertEquals(updatedTask.getName(), foundTask.getName());
        assertEquals(updatedTask.getDescription(), foundTask.getDescription());
        assertEquals(updatedTask.getStatus(), foundTask.getStatus());
    }

    @Test
    // Проверяет удаление задачи по идентификатору.
    void testDeleteById() {
        taskController.add(task1);
        taskController.deleteById(task1.getId());
        Task foundTask = taskController.findById(task1.getId());
        assertNull(foundTask);
    }

    @Test
    // Проверяет удаление всех задач.
    void testDeleteAll() {
        taskController.add(task1);
        taskController.add(task2);
        taskController.deleteAll();
        ArrayList<Task> tasks = taskController.findAll();
        assertTrue(tasks.isEmpty());
    }
}
