package test.ru.yandex.javacourse.kanban.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.kanban.manager.InMemoryTaskManager;
import ru.yandex.javacourse.kanban.tasks.Epic;
import ru.yandex.javacourse.kanban.tasks.Status;
import ru.yandex.javacourse.kanban.tasks.SubTask;
import ru.yandex.javacourse.kanban.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestInMemoryTaskManager {
    private InMemoryTaskManager taskManager;
    private Task task1;
    private Task task2;
    private Epic epic1;
    private SubTask subTask1;
    private SubTask subTask2;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
        task1 = new Task("Task 1", "Description for Task 1", 1, Status.NEW);
        task2 = new Task("Task 2", "Description for Task 2", 2, Status.NEW);
        epic1 = new Epic("Epic 1", "Description for Epic 1", 3);
        subTask1 = new SubTask("SubTask 1", "Description for SubTask 1", 4, epic1.getId());
        subTask2 = new SubTask("SubTask 2", "Description for SubTask 2", 5, epic1.getId());
    }

    @Test
    // Проверяет добавление и поиск задачи.
    void testAddAndFindTask() {
        taskManager.addTask(task1);
        Task foundTask = taskManager.findTaskById(task1.getId());
        assertEquals(task1, foundTask);
    }

    @Test
    // Проверяет добавление и поиск эпика.
    void testAddAndFindEpic() {
        taskManager.addEpic(epic1);
        Epic foundEpic = taskManager.findEpicById(epic1.getId());
        assertEquals(epic1, foundEpic);
    }

    @Test
    // Проверяет добавление и поиск подзадачи.
    void testAddAndFindSubTask() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1, epic1);
        SubTask foundSubTask = taskManager.findSubTaskById(subTask1.getId());
        assertEquals(subTask1, foundSubTask);
    }

    @Test
    // Проверяет получение всех задач.
    void testFindAllTasks() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        ArrayList<Task> tasks = taskManager.findAllTasks();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    // Проверяет получение всех эпиков.
    void testFindAllEpics() {
        taskManager.addEpic(epic1);
        ArrayList<Epic> epics = taskManager.findAllEpics();
        assertEquals(1, epics.size());
        assertTrue(epics.contains(epic1));
    }

    @Test
    // Проверяет получение всех подзадач эпика.
    void testFindAllSubTasksOfEpic() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1, epic1);
        taskManager.addSubTask(subTask2, epic1);
        ArrayList<SubTask> subTasks = taskManager.findAllSubTasksOfEpic(epic1);
        assertEquals(2, subTasks.size());
        assertTrue(subTasks.contains(subTask1));
        assertTrue(subTasks.contains(subTask2));
    }

    @Test
    // Проверяет обновление задачи.
    void testUpdateTaskByID() {
        taskManager.addTask(task1);
        Task updatedTask = new Task("Updated Task 1", "Updated Description for Task 1", 1, Status.IN_PROGRESS);
        taskManager.updateTaskByID(updatedTask);
        Task foundTask = taskManager.findTaskById(task1.getId());
        assertEquals(updatedTask, foundTask);
    }

    @Test
    // Проверяет обновление подзадачи.
    void testUpdateSubTaskByID() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1, epic1);
        SubTask updatedSubTask = new SubTask("Updated SubTask 1", "Updated Description for SubTask 1", 4, epic1.getId());
        taskManager.updateSubTaskByID(updatedSubTask);
        SubTask foundSubTask = taskManager.findSubTaskById(subTask1.getId());
        assertEquals(updatedSubTask, foundSubTask);
    }

    @Test
    // Проверяет обновление эпика.
    void testUpdateEpicByID() {
        taskManager.addEpic(epic1);
        Epic updatedEpic = new Epic("Updated Epic 1", "Updated Description for Epic 1", 3);
        taskManager.updateEpicByID(updatedEpic);
        Epic foundEpic = taskManager.findEpicById(epic1.getId());
        assertEquals(updatedEpic, foundEpic);
    }

    @Test
    // Проверяет удаление всех задач.
    void testDeleteAllTasks() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.deleteAllTask();
        ArrayList<Task> tasks = taskManager.findAllTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    // Проверяет удаление всех подзадач.
    void testDeleteAllSubTasks() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1, epic1);
        taskManager.addSubTask(subTask2, epic1);
        taskManager.deleteAllSubTasks();
        ArrayList<SubTask> subTasks = taskManager.findAllSubTasksOfEpic(epic1);
        assertTrue(subTasks.isEmpty());
    }

    @Test
    // Проверяет удаление всех эпиков.
    void testDeleteAllEpics() {
        taskManager.addEpic(epic1);
        taskManager.deleteAllEpics();
        ArrayList<Epic> epics = taskManager.findAllEpics();
        assertTrue(epics.isEmpty());
    }

    @Test
    // Проверяет удаление задачи по идентификатору.
    void testDeleteTaskById() {
        taskManager.addTask(task1);
        taskManager.deleteTaskById(task1.getId());
        Task foundTask = taskManager.findTaskById(task1.getId());
        assertNull(foundTask);
    }

    @Test
    // Проверяет удаление подзадачи по идентификатору.
    void testDeleteSubTaskById() {
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1, epic1);
        taskManager.deleteSubTaskById(subTask1.getId());
        SubTask foundSubTask = taskManager.findSubTaskById(subTask1.getId());
        assertNull(foundSubTask);
    }

    @Test
    // Проверяет удаление эпика по идентификатору.
    void testDeleteEpicById() {
        taskManager.addEpic(epic1);
        taskManager.deleteEpicById(epic1.getId());
        Epic foundEpic = taskManager.findEpicById(epic1.getId());
        assertNull(foundEpic);
    }

    @Test
    // Проверяет получение истории задач.
    void testGetHistory() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.findTaskById(task1.getId());
        taskManager.findTaskById(task2.getId());
        List<Task> history = taskManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }
}
